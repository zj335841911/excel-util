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
     * @param targetFilePath  目标表名文件路径
     * @param bucketsFilePath  分桶清单文件路径
     * @Description 根据传入路径自动生成ITL创表sql语句
     * @author zj
     * @since 2020/3/24 09:40
     */
    public void itlSqlgenerate(String filePath, String targetFilePath, String bucketsFilePath) throws FileNotFoundException, ClassNotFoundException {
        if ("".equals(StringUtil.trimStr(filePath))) throw new RuntimeException("请输入源文件的文件路径");
        //获取目标表名数据集合
//        ArrayList<String> names_itl = eg.getSourceTables("./src/test/resources/ITL_RPT_日数据量统计_20200119(1).xlsx", "itl");
        ArrayList<String> names_itl = getSourceTables(targetFilePath, "itl");
        //用于存储根据表名分类后的数据
        Map<String, List<DBModel>> contents = null;
        //根据给定的路径读取excel生成相应的数据仓模型数据
        Collection<DBModel> tables = importDBModelToClass(filePath);
        //根据给定的路径读取excel生成相应的分桶数据
//        Collection<ITL_Buckets> itl_buckets = importITL_BucketsToClass("./src/test/resources/ITL_分桶清单.xlsx");
        Collection<ITL_Buckets> itl_buckets = importITL_BucketsToClass(bucketsFilePath);
        ArrayList<ITL_Buckets> itl_buckets1 = (ArrayList<ITL_Buckets>) itl_buckets.stream()
                .filter(x -> "是".equals(x.get是否分桶最终())).collect(Collectors.toList());
        //提取表中表名与目标表表名相同的数据，并根据表名对集合数据进行分类
        contents = tables.stream()
                .filter(x -> names_itl.contains(x.get表名().toUpperCase()))
                .collect(Collectors.groupingBy(DBModel::get表名));
        contents.entrySet().forEach(
                x -> {
                    StringBuffer sql = new StringBuffer();
                    sql.append("CREATE TABLE " + x.getKey() + "(\r\n");
                   List<DBModel> dbModels = x.getValue();
                    //向数仓模型创表sql语句文件添加内容
                    addContentToSql(sql, dbModels);
                    String sql1 = sql.substring(0,sql.lastIndexOf(",\r\n"));
                    StringBuffer sql2 = new StringBuffer();
                    sql2.append(sql1 + "\r\n) COMMENT \"" + x.getValue().get(0).get表名备注() + "\""
                            + "\r\nPARTITIONED BY  (ETL_DT STRING)");
                    itl_buckets1.stream().forEach(c -> {
                        if (c.get表名().toUpperCase().equals(x.getKey())){
                            sql2.append("\r\nclustered  by  (" + c.get分布键() + ")  into    "
                                    + c.get分桶个数取质数().intValue() + "   buckets" );
                        }
                    });
                    sql2.append("\r\nSTORED AS  PARQUET;");
                    //写入文件,文件路径为固定的相对路径
                    String filePath1 = new File("").getAbsolutePath() + "\\out\\Itl" + "\\" + x.getKey() + ".sql";
                    write2File(filePath1, sql2.toString());
                }
        );
        System.out.println("itl创表语句sql生成成功");
    }


    /**
     * @param filePath  数仓模型文件路径
     * @param targetFilePath  目标表名文件路径
     * @param bucketsFilePath  分桶清单文件路径
     * @Description 根据传入路径自动生成RPT创表sql语句
     * @author zj
     * @since 2020/3/26 09:40
     */
    public void rptSqlgenerate(String filePath, String targetFilePath, String bucketsFilePath) throws FileNotFoundException, ClassNotFoundException {
        if ("".equals(StringUtil.trimStr(filePath))) throw new RuntimeException("请输入源文件的文件路径");
        //获取目标表名数据集合
//        ArrayList<String> names_rpt = eg.getSourceTables("./src/test/resources/ITL_RPT_日数据量统计_20200119(1).xlsx", "rpt");
        ArrayList<String> names_rpt = getSourceTables(targetFilePath, "rpt");
        //用于存储根据表名分类后的数据
        Map<String, List<DBModel>> contents = null;
        //根据给定的路径读取excel生成相应的数据仓模型数据
        Collection<DBModel> tables = importDBModelToClass(filePath);
        //根据给定的路径读取excel生成相应的分桶数据
//        Collection<RPT_Buckets> rpt_buckets = importRPT_BucketsToClass("./src/test/resources/RPT_分桶清单.xlsx");
        Collection<RPT_Buckets> rpt_buckets = importRPT_BucketsToClass(bucketsFilePath);
        ArrayList<RPT_Buckets> rpt_buckets1 = (ArrayList<RPT_Buckets>) rpt_buckets.stream()
                .filter(x -> "是".equals(x.get是否分桶最终())).collect(Collectors.toList());
        //提取表中表名与目标表表名相同的数据，并根据表名对集合数据进行分类
        contents = tables.stream()
                .filter(x -> names_rpt.contains(x.get表名().toUpperCase()))
                .collect(Collectors.groupingBy(DBModel::get表名));
        contents.entrySet().forEach(
                x -> {
                    StringBuffer sql = new StringBuffer();
                    sql.append("CREATE TABLE " + x.getKey() + "(\r\n");
                    List<DBModel> dbModels = x.getValue();
                    //向数仓模型创表sql语句文件添加内容
                    addContentToSql(sql, dbModels);
                    String sql1 = sql.substring(0,sql.lastIndexOf(",\r\n"));
                    StringBuffer sql2 = new StringBuffer();
                    sql2.append(sql1 + "\r\n) COMMENT \"" + x.getValue().get(0).get表名备注() + "\""
                            + "\r\nPARTITIONED BY  (STAT_DT STRING)");
                    rpt_buckets1.stream().forEach(c -> {
                        if (c.get表名().toUpperCase().equals(x.getKey())){
                            sql2.append("\r\nclustered  by  (" + c.get分布键() + ")  into    "
                                    + c.get分桶个数取质数().intValue() + "   buckets" );
                        }
                    });
                    sql2.append("\r\nSTORED AS  PARQUET;");
                    //写入文件,文件路径为固定的相对路径
                    String filePath1 = new File("").getAbsolutePath() + "\\out\\Rpt" + "\\" + x.getKey() + ".sql";
                    write2File(filePath1, sql2.toString());
                }
        );
        System.out.println("rpt创表语句sql生成成功");
    }


    /**
     * @param filePath  文件路径
     * @param sheetName 传入itl或者rpt
     * @return java.util.Set<java.lang.String>
     * @Description 获取元数据的表名数据
     * @author Swagger-Ranger
     * @since 2020/2/24 18:34
     */
    private ArrayList<String> getSourceTables( String filePath, String sheetName ) throws FileNotFoundException, ClassNotFoundException {

        File f = new File(filePath);
        InputStream in = new FileInputStream(f);

        ExcelLogs logs = new ExcelLogs();
        Class clazz = null;
        clazz = Class.forName("com.myccb.Entity.mapper.ITL_RPT");

        ArrayList<ITL_RPT> tables = (ArrayList<ITL_RPT>) ExcelUtil.importStringSheetToClass(clazz, in, sheetName, 1, logs);
        ArrayList<String> tableNames = (ArrayList<String>) tables.stream()
                .map(ITL_RPT::getTableName)
                .map(String::toUpperCase).collect(Collectors.toList());
        return tableNames;
    }


    /**
     * @param filePath 文件路径
     * @return java.util.Collection<com.myccb.Entity.mapper.DBModel>
     * @Description 获取数仓模型数据
     * @author zj
     * @since 2020/3/24 18:33
     */
    private Collection<DBModel> importDBModelToClass(String filePath ) throws FileNotFoundException {

        File f = new File(filePath);
        InputStream in = new FileInputStream(f);

        ExcelLogs logs = new ExcelLogs();
        Class clazz = null;
        try {
            clazz = Class.forName("com.myccb.Entity.mapper.DBModel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Collection<DBModel> tables = ExcelUtil.importStringSheetToClass(clazz, in, "0", 1, logs);
        return tables;
    }


    /**
     * @param filePath 文件路径
     * @return java.util.Collection<com.myccb.Entity.mapper.ITL_Buckets>
     * @Description 获取itl分桶清单数据
     * @author zj
     * @since 2020/3/24 18:33
     */
    private Collection<ITL_Buckets> importITL_BucketsToClass(String filePath ) throws FileNotFoundException {

        File f = new File(filePath);
        InputStream in = new FileInputStream(f);

        ExcelLogs logs = new ExcelLogs();
        Class clazz = null;
        try {
            clazz = Class.forName("com.myccb.Entity.mapper.ITL_Buckets");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Collection<ITL_Buckets> tables = ExcelUtil.importSheetCellToClass(clazz, in, "0", 1, logs);
        return tables;
    }


    /**
     * @param filePath 文件路径
     * @return java.util.Collection<com.myccb.Entity.mapper.ITL_Buckets>
     * @Description 获取rpt分桶清单数据
     * @author zj
     * @since 2020/2/26 18:33
     */
    private Collection<RPT_Buckets> importRPT_BucketsToClass(String filePath ) throws FileNotFoundException {

        File f = new File(filePath);
        InputStream in = new FileInputStream(f);

        ExcelLogs logs = new ExcelLogs();
        Class clazz = null;
        try {
            clazz = Class.forName("com.myccb.Entity.mapper.RPT_Buckets");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Collection<RPT_Buckets> tables = ExcelUtil.importSheetCellToClass(clazz, in, "0", 1, logs);
        return tables;
    }

    /**
     * @param sql 数仓模型创表sql语句
     * @param dbModels     数仓实体集合
     * @Description 向数仓模型创表sql语句文件添加内容
     * @author zj
     * @since 2020/4/1 15:48
     */
    private void addContentToSql(StringBuffer sql, List<DBModel> dbModels) {
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
                    if (y.get表名().startsWith("T") ? "ETL_DT".equals(y.get列名()) : y.get列名().equals("STAT_DT")){
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


    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException{
        DWExcelSqlGenerator eq=new DWExcelSqlGenerator();
        eq.itlSqlgenerate("./src/test/resources/（数仓模型）ITL层.xlsx","./src/test/resources/ITL_RPT_日数据量统计_20200119(1).xlsx","./src/test/resources/ITL_分桶清单.xlsx");
        eq.rptSqlgenerate("./src/test/resources/（数仓模型）RPT层.xlsx","./src/test/resources/ITL_RPT_日数据量统计_20200119(1).xlsx","./src/test/resources/RPT_分桶清单.xlsx");
    }


}
