package com.myccb.Generator;

import com.myccb.Entity.mapper.*;
import com.myccb.util.ExcelUtil.ExcelLogs;
import com.myccb.util.ExcelUtil.ExcelUtil;
import com.myccb.util.StringUtil;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 生成数仓模型创表sql语句的工具类
 * @author zj
 * @version 1.0
 * @date 2020/03/24 09:14
 */
public class DWExcelSqlGenerator {

    private static final Map<String, String> DataTypeMap;
    static {
        //初始化类型映射关系表
        Map<String, String> map = new HashMap<>();
        map.put("CHAR", "STRING");
        map.put("VARCHAR", "STRING");
        map.put("NCHAR", "STRING");
        map.put("NVARCHAR", "STRING");
        map.put("VARCHAR2", "STRING");
        map.put("NVARCHAR2", "STRING");
        map.put("DECIMAL", "DECIMAL(38,6)");
        map.put("BIT", "BOOLEAN");
        map.put("BOOLEAN", "BOOLEAN");
        map.put("SMALLINT", "DECIMAL(38,0)");
        map.put("INTEGER", "DECIMAL(38,0)");
        map.put("LONG", "BINARY");
        map.put("LONG RAW", "BINARY");
        map.put("RAW", "BINARY");
        map.put("FLOAT", "DECIMAL(38,6)");
        map.put("BINARY_FLOAT", "FLOAT");
        map.put("DOUBLE", "DECIMAL(38,6)");
        map.put("BINARY_DOUBLE", "DOUBLE");
        map.put("CLOB", "STRING");
        map.put("NCLOB", "BINARY");
        map.put("BFILE", "BINARY");
        map.put("DATE", "STRING");
        map.put("TIMESTAMP", "STRING");
        map.put("TIMDATEESTAMP", "TIMESTAMP");
        map.put("INTERVAL YEAR TO MONTH", "INTERVAL_YEAR_MONTH");
        map.put("INTERVAL DAY TO SECOND", "INTERVAL_DAY_TIME");
        map.put("STRUCT", "STRUCT");
        map.put("ARRAY", "ARRAY");
        DataTypeMap = Collections.unmodifiableMap(map);
    }


    /**
     * @param filePath  数仓模型文件路径
     * @param sheetName  页签名
     * @Description 根据传入路径自动生成ITL创表sql语句
     * @author zj
     * @since 2020/3/24 09:40
     */
    public void sqlgenerate(String filePath, String sheetName) throws FileNotFoundException {
        if ("".equals(StringUtil.trimStr(filePath))) throw new RuntimeException("请输入源文件的文件路径");
        //用于存储根据表名分类后的数据
        Map<String, List<DBModel1>> contents = null;
        //根据给定的路径读取excel生成相应的数据仓模型数据
        Collection<DBModel1> tables = importDBModelToClass(filePath, sheetName);
        //根据表名对集合数据进行分类
        contents = tables.stream()
                .collect(Collectors.groupingBy(DBModel1::get表名));
        contents.entrySet().forEach(
                x -> {
                    StringBuffer sql = new StringBuffer();
                    sql.append("CREATE TABLE " + x.getKey() + "(\r\n");
                    List<DBModel1> dbModels = x.getValue();
                    //向数仓模型创表sql语句文件添加内容
                    addContentToSql(sql, dbModels, sheetName);
                    String sql1 = sql.substring(0,sql.lastIndexOf(",\r\n"));
                    StringBuffer sql2 = new StringBuffer();
                    sql2.append(sql1 + "\r\n) COMMENT \"" + x.getValue().get(0).get表名备注() + "\""
                            + ("ITL".equals(sheetName) ? "\r\nPARTITIONED BY  (ETL_DT STRING)" : "\r\nPARTITIONED BY  (STAT_DT STRING)"));
                    List<DBModel1> bucket = dbModels.stream().filter(y -> "是".equals(y.get是否分布键())).collect(Collectors.toList());
                    if (bucket != null && bucket.size() != 0){
                        sql2.append("\r\nclustered  by  (" );
                        bucket.stream().forEach(a -> {
                            if (bucket.size() - 1 == bucket.indexOf(a)){
                                sql2.append(a.get列名());
                            }else {
                                sql2.append(a.get列名() + ",");
                            }
                        });
                        sql2.append(")  into    " + bucket.get(0).get分桶个数().intValue() + "   buckets");
                    }
                    sql2.append("\r\nSTORED AS  PARQUET;");
                    //写入文件,文件路径为固定的相对路径
                    String filePath1 = new File("").getAbsolutePath() + "\\out\\" + sheetName +"\\cereteSql\\"
                            + x.getKey() + ".sql";
                    write2File(filePath1, sql2.toString());
                    if ("ITL".equals(sheetName)){
                        StringBuffer etSql = new StringBuffer();
                        etSql.append("CREATE TABLE ET_" + x.getKey() + "(\r\n");
                        dbModels.stream().forEach(
                                y ->{
                                    if ("ITL".equals(sheetName) ? "ETL_DT".equals(y.get列名()) : y.get列名().equals("STAT_DT")){
                                        etSql.append("");
                                    }else {
                                        etSql.append(y.get列名() + "   " + y.get数据类型() + "  COMMENT \"" + y.get列名备注() + "\",\r\n");
                                    }
                                }
                        );
                        String etSql1 = etSql.substring(0,etSql.lastIndexOf(",\r\n"));
                        StringBuffer etSql2 = new StringBuffer();
                        etSql2.append(etSql1 + "\r\n) COMMENT \"" + x.getValue().get(0).get表名备注() + "\"" + "\r\nROW"
                                + " FORMAT DELIMITED FIELDS TERMINATED BY \"\\001\" \r\nLOCATION \"/data/input/"
                                + x.getKey() + "\";");
                        //写入文件,文件路径为固定的相对路径
                        String etFilePath = new File("").getAbsolutePath() + "\\out\\" + sheetName +"\\etcereteSql\\"
                                + "ET_" + x.getKey() + ".sql";
                        write2File(etFilePath, etSql2.toString());
                    }
                }
        );
    }

    /**
     * @param filePath 文件路径
     *
     * @return java.util.Collection<com.myccb.Entity.mapper.DBModel1>
     * @Description 获取数仓模型数据
     * @author Swagger-Ranger
     * @since 2020/2/24 18:33
     */
    private Collection<DBModel1> importDBModelToClass( String filePath, String sheetName ) throws FileNotFoundException {

        File f = new File(filePath);
        InputStream in = new FileInputStream(f);

        ExcelLogs logs = new ExcelLogs();
        Class clazz = null;
        try {
            clazz = Class.forName("com.myccb.Entity.mapper.DBModel1");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Collection<DBModel1> tables = ExcelUtil.importSheetCellToClass(clazz, in, sheetName, 1, logs);
        return tables;
    }

    /**
     * @param sql 数仓模型创表sql语句
     * @param dbModels     数仓实体集合
     * @Description 向数仓模型创表sql语句文件添加内容
     * @author zj
     * @since 2020/4/1 15:48
     */
    private void addContentToSql(StringBuffer sql, List<DBModel1> dbModels, String sheetName ) {
        dbModels.stream().forEach(
                y -> {
                    if (Arrays.asList ("CHANGE", "CHARACTER", "COMPRESSION", "DATA", "DATETIME", "FLOOR",
                            "GROUPS", "IGNORE","LIMIT","LOAD","LOCATION","PARTITION","PARTITIONED","POSITION",
                            "REDUCE","ROLE","STRUCT","TIMESTAMP","DESCRIBE").contains(y.get列名())){
                        y.set列名("RE__"+y.get列名());
                    }
                    String a = "";

                    if (!y.get数据类型().equals("NUMBER")) {
                        y.set数据类型(DataTypeSwitcher(y.get数据类型()));
                    }else {
                        if (y.get整数位() != null && y.get小数位() != null){
                            y.set数据类型("DECIMAL(" + y.get整数位() + "," + y.get小数位() + ")");
                        }else if (y.get整数位() == null && y.get小数位() != null){
                            y.set数据类型("DECIMAL(38" + "," + y.get小数位() + ")");
                        }else if (y.get整数位() != null && y.get小数位() == null){
                            y.set数据类型("DECIMAL(" + y.get整数位() + ",0)");
                        }else {
                            y.set数据类型("DECIMAL(38,6)");
                        }
                    }
                    if ("ITL".equals(sheetName) ? "ETL_DT".equals(y.get列名()) : y.get列名().equals("STAT_DT")){
                        a = "";
                    }else {
                        a = y.get列名() + "   " + y.get数据类型() + "  COMMENT \"" + y.get列名备注() + "\",\r\n";
                    }
                    sql.append(a);
                }
        );

    }

    /**
     * 转换数据类型
     *
     * @param sourceType 源数据类型
     * @return
     */
    private String DataTypeSwitcher( String sourceType ) {

        //忽略类型中如TIMESTEMP(6)等字段的长度限定
        String type = sourceType;
        int loc = sourceType.indexOf('(');
        if (loc > 0) {
            type = sourceType.substring(0, loc);
        }
        return DataTypeMap.get(type);
    }


    /**
     * @param outFilePath 输出文件路径
     * @param content     文件内容
     * @Description 将内容写入文件
     * @author Swagger-Ranger
     * @since 2020/2/12 22:48
     */
    public void write2File( String outFilePath, String content ) {
        File file = new File(outFilePath);
        makeDirRecurse(file.getParentFile());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("目标文件路径有误，请确认输出文件路径");
            }
        }

        try (
                FileOutputStream fos = new FileOutputStream(outFilePath)
        ) {
            byte[] bytes = content.getBytes();
            int length = bytes.length;
            int index = 0;
            while (index < length) {
                fos.write(bytes, index, index + 100 <= length ? 100 : length - index);
                index += 100;
            }
        } catch (IOException e) {
            System.err.println("目标文件路径有误，请确认输出文件路径");
        }
    }

    /**
     * @param file 目录地址
     * @return void
     * @Description 递归生成输出文件的目录
     * @author Swagger-Ranger
     * @since 2020/2/17 19:01
     */
    private void makeDirRecurse( File file ) {
        if (file.getParentFile().exists()) {
            file.mkdir();
        } else {
            makeDirRecurse(file.getParentFile());
            file.mkdir();
        }
    }


    public static void main(String[] args) throws FileNotFoundException{
        DWExcelSqlGenerator eq=new DWExcelSqlGenerator();
        eq.sqlgenerate("E:\\MYSH\\needExcel\\数仓模型.xlsx","RPT");

    }


}
