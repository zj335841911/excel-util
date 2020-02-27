package com.myccb.Generator;

import com.myccb.Entity.mapper.DBModel;
import com.myccb.util.ExcelUtil.ExcelUtil;
import com.myccb.util.StringUtil;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author Email:liufei32@outlook.com  github:Swagger-Ranger
 * @Date 2020/2/26 16:23
 * @Description DDL语句转换为对应格式的Java类
 */

public class DDLParser {

    //最终解析出来的结果，列名是关键属性。下面的方法都操作这个公共的类属性
    private LinkedHashMap<String, DBModel> rows = new LinkedHashMap<>();

    public static void main( String[] args ) throws IOException, ClassNotFoundException {

        DDLParser ddlParser = new DDLParser();
//        ddlParser.parseTable();
//        System.out.println(Arrays.toString(ddlParser.getProperties(" VARCHAR2(20 CHAR),")));
//        System.out.println(Arrays.toString(ddlParser.getProperties(" NUMBER(5)")));
//        System.out.println(Arrays.toString(ddlParser.getProperties("DATE")));
//        System.out.println(Arrays.toString(ddlParser.getProperties("NUMBER(8,6),")));

        String con = ddlParser.getContent("./src/test/resources/" + "missingTables/T_TST_PCPED_DDL.sql");
        ddlParser.parseRow(con);
        ddlParser.parseComment(con);
        System.out.println(ddlParser.rows.size());
        ddlParser.write2File();


//        System.out.println(Arrays.toString("  asd,f".split("(\\,|\\s+)")));


    }

    public void parseTable() throws IOException, ClassNotFoundException {
        String path = "./src/test/resources/";
        String[] files = {
                "missingTables/T_CAS_TC_SUP_PRM_AST_PAY_TYP_DDL.sql", "missingTables/T_CMS_TC_SUP_LOAN_INFO_DDL.sql",
                "missingTables/T_CMS_TC_SUP_TRUST_PAY_ACCT_DDL.sql", "missingTables/T_NIB_TRAN_SEQ_DDL.sql",
                "missingTables/T_TST_PCPED_DDL.sql"};
        for (int i = 0; i < files.length; i++) {

            String data = getContent(path + files[i]);
            parseRow(data);
            parseComment(data);
        }
        write2File();

    }

    private void write2File() throws ClassNotFoundException, IOException {
        //写入新的excel
        Collection<DBModel> datas = rows.values();
//        datas.stream().sorted(Comparator.comparing(DBModel::get表名));

        Map<String, String> map = new LinkedHashMap<>();
        Class clazz = Class.forName("com.myccb.Entity.mapper.DBModel");
        Field[] fieldsArr = clazz.getDeclaredFields();
        for (int i = 0; i < fieldsArr.length; i++) {
            map.put(fieldsArr[i].getName(), fieldsArr[i].getName());
        }
        ArrayList<DBModel> dataset = new ArrayList<>();
        dataset.addAll(datas);

        File f = new File("Missing.xls");
        OutputStream out = new FileOutputStream(f);
        ExcelUtil.exportExcel(map, dataset, out);
        out.close();

    }

    /**
     * @param DDLString 建表语句的建表字符串
     * @return void
     * @Description 解析创建表的sql语句：对应列名，数据类型，字段长度，整数位，小数位
     * @author Swagger-Ranger
     * @since 2020/2/26 16:54
     */
    private void parseRow( String DDLString ) {
        /**
         * create table T_CMS_TC_SUP_TRUST_PAY_ACCT
         * (
         *   rcv_date    VARCHAR2(16 CHAR),
         *   pay_name    VARCHAR2(200 CHAR),
         *   pay_amt     NUMBER(17,2),
         *   create_time TIMESTAMP(6),
         *   etl_dt      DATE
         * )
         * partition by list (ETL_DT)
         */
        String getCore = DDLString.substring(DDLString.indexOf("create table "), DDLString.indexOf("partition by"));
        String tableName = getCore.substring("create table ".length(), getCore.indexOf("\n"));
        String getData = getCore.substring(getCore.indexOf("(") + 1, getCore.lastIndexOf(")"));

        String row[] = getData.split("\\n");
        for (int i = 0; i < row.length; i++) {
            DBModel dbModel = new DBModel();
            String[] s = row[i].trim().split("\\s+");
            if (s.length < 2) continue;
            dbModel.set表名(tableName);
            dbModel.set列名(s[0].toUpperCase());
            String[] properties = getProperties(s[1]);
            dbModel.set数据类型(properties[0]);
            dbModel.set字段长度(properties[1]);
            dbModel.set整数位(properties[2]);
            dbModel.set小数位(properties[3]);
            rows.put(dbModel.get列名(), dbModel);
        }
    }

    /**
     * 解析属性：VARCHAR2(16 CHAR), 返回对应4个字段：[数据类型，字段长度，整数位，小数位]
     *
     * @param property
     * @return
     */
    private String[] getProperties( String property ) {
        String[] properties = new String[4];

        //要么是类型，要么类型中还加了长度限定，有()的情况也只有类型没有长度
        String[] a = property.trim().split("(\\,|\\s+)");
        properties[0] = a[0];

        //如果有()的情况:[VARCHAR2(16, CHAR)]
        if (a.length > 1) {
            String[] b = properties[0].split("\\(");
            properties[0] = b[0];
            properties[1] = b[1];
            properties[2] = b[1];

            //处理后面一段:CHAR)
            String c = a[1].substring(0, a[1].indexOf(")"));
            if (StringUtil.isNumberString(c)) {
                properties[3] = c;
                properties[1] = b[1] + "," + c;
            }
        }
        if ("DATE".equals(properties[0])) {
            properties[1] = "7";
            properties[2] = "7";
        }

        String[] deal0 = properties[0].split("\\(");
        if (deal0.length > 1) {

            properties[0] = deal0[0];
            properties[1] = deal0[1];
            properties[2] = deal0[1];

            if (properties[0].contains(")") && properties[0].trim().startsWith("NUM")) {
                String len = properties[0].substring(properties[0].indexOf("(") + 1, properties[0].indexOf(")"));
                properties[2] = len;
                properties[1] = len;
            }


        }
        return properties;

    }

    /**
     * @param DDLString 建表语句的备注字符串
     * @return void
     * @Description 解析创建表： 表名，表名备注，列名备注
     * @author Swagger-Ranger
     * @since 2020/2/26 16:56
     */
    private void parseComment( String DDLString ) {

        /**
         * -- Add comments to the table
         * comment on table T_CMS_TC_SUP_TRUST_PAY_ACCT
         *   is '受托支付账户信息表';
         * -- Add comments to the columns
         * comment on column T_CMS_TC_SUP_TRUST_PAY_ACCT.rcv_date
         *   is '登记日期';
         * -- Grant/Revoke object privileges
         */

        String getComments = DDLString.substring(DDLString.indexOf("-- Add comments to the columns"), DDLString.indexOf("-- Grant"));
        for (String s : rows.keySet()) {
            int start = getComments.indexOf(s.toLowerCase());
            String rowComment = getComments.substring(
                    getComments.indexOf("is '", start) + "is '".length(),
                    getComments.indexOf("';", start));
            rows.get(s).set列名备注(rowComment);
        }

    }

    /**
     * @param filePath 文件路径
     * @return java.lang.String
     * @Description 读取文件中的DDL文本
     * @author Swagger-Ranger
     * @since 2020/2/26 17:07
     */
    private String getContent( String filePath ) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        FileReader fr = new FileReader(filePath);
        char[] cs = new char[1024];
        int len;
        while ((len = fr.read(cs)) != -1) {
            stringBuilder.append(new String(cs, 0, len));
        }
        fr.close();

        return stringBuilder.toString();
    }

}
