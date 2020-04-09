package com.myccb.Generator;

import com.myccb.Entity.mapper.DBModel;
import com.myccb.Entity.mapper.ITL_RPT;
import com.myccb.Entity.mapper.Mapper;
import com.myccb.util.ExcelUtil.ExcelLogs;
import com.myccb.util.ExcelUtil.ExcelUtil;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Email:liufei32@outlook.com  github:Swagger-Ranger
 * @Date 2020/2/24 15:43
 * @Description 生成Mapper关系的工具类
 */

public class NewMapperGenerator {

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
        map.put("DECIMAL", "DECIMAL");
        map.put("BIT", "BOOLEAN");
        map.put("BOOLEAN", "BOOLEAN");
        map.put("SMALLINT", "DECIMAL");
        map.put("INTEGER", "DECIMAL");
        map.put("LONG", "BINARY");
        map.put("RAW", "BINARY");
        map.put("FLOAT", "DECIMAL");
        map.put("BINARY_FLOAT", "FLOAT");
        map.put("DOUBLE", "DECIMAL");
        map.put("BINARY_DOUBLE", "DOUBLE");
        map.put("CLOB", "STRING");
        map.put("NCLOB", "BINARY");
        map.put("BFILE", "BINARY");
        map.put("DATE", "TIMESTAMP");
        map.put("TIMDATEESTAMP", "TIMESTAMP");
        map.put("STRUCT", "STRUCT");
        map.put("ARRAY", "ARRAY");
        map.put("NUMBER", "DECIMAL");
        map.put("TIMESTAMP", "TIMESTAMP");
        DataTypeMap = Collections.unmodifiableMap(map);
    }

    public static void main( String[] args ) throws IOException, ClassNotFoundException {
        new NewMapperGenerator().itlGenerateMapper("./src/test/resources/数仓（ITL层）_字段修正v2.1.xlsx","./src/test/resources/ITL_RPT_日数据量统计_20200119(1).xlsx");
    }

    /**
     * @param filePath  数仓模型文件路径
     * @param targetFilePath  目标表名文件路径
     * @Description 根据传入路径自动生成itlMapper.xls文件
     * @author zj
     * @since 2020/4/01 09:40
     */
    public void itlGenerateMapper(String filePath, String targetFilePath) throws IOException, ClassNotFoundException {
        //获取表名数据集合
//        ArrayList<String> names_itl = getSourceTables("./src/test/resources/ITL_RPT_日数据量统计_20200119(1).xlsx", "itl");
        ArrayList<String> names_itl = getSourceTables(targetFilePath, "itl");
        //根据表名分别获取rpt和itl的模型数据，也就是表名获取对应字段然后产生新的mapper对象
//        ArrayList<DBModel> models_itl = (ArrayList<DBModel>) importDBModelToClass("./src/test/resources/数仓（ITL层）_字段修正v2.1.xlsx");
        ArrayList<DBModel> models_itl = (ArrayList<DBModel>) importDBModelToClass(filePath);
        //获取到的mapper对象
        ArrayList<Mapper> mapperSet = getMapperSet(models_itl, names_itl);

        //获取到没有映射到的表名数据
        Set<String> itlUnmapped = models_itl.stream().map(DBModel::get表名).collect(Collectors.toSet());
        List<String> unMapped_itl = names_itl.stream().filter(x -> !itlUnmapped.contains(x.toUpperCase())).collect(Collectors.toList());

        ArrayList<Mapper> mapperSetUnMapped = (ArrayList<Mapper>) unMapped_itl.stream()
                .map(x -> new Mapper().setGroup("UnMapped").setTARGET_TABLE(x.toUpperCase()))
                .collect(Collectors.toList());

        //写入新的excel
        Map<String, String> map = new LinkedHashMap<>();
        Class clazz = Class.forName("com.myccb.Entity.mapper.Mapper");
        Field[] fieldsArr = clazz.getDeclaredFields();
        for (int i = 0; i < fieldsArr.length; i++) {
            map.put(fieldsArr[i].getName(), fieldsArr[i].getName());
        }
        ArrayList<Mapper> dataset = new ArrayList<>();
        dataset.addAll(mapperSet);
        dataset.addAll(mapperSetUnMapped);

        String filePath1 = new File("").getAbsolutePath() + "\\out\\mapper" + "\\" + "itlMapper.xls";
        File f = new File(filePath1);
        makeDirRecurse(f.getParentFile());
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.err.println("目标文件路径有误，请确认输出文件路径");
            }
        }
        OutputStream out = new FileOutputStream(f);
        ExcelUtil.exportExcel(map,dataset,out,null,"ITL");
        out.close();
    }



    /**
     * @param filePath  数仓模型文件路径
     * @param targetFilePath  目标表名文件路径
     * @Description 根据传入路径自动生成rptMapper.xls文件
     * @author zj
     * @since 2020/4/01 09:40
     */
    public void rptGenerateMapper(String filePath, String targetFilePath) throws IOException, ClassNotFoundException {
        //获取表名数据集合
//        ArrayList<String> names_rpt = getSourceTables("./src/test/resources/ITL_RPT_日数据量统计_20200119(1).xlsx", "rpt");
        ArrayList<String> names_rpt = getSourceTables(targetFilePath, "rpt");
        //根据表名分别获取rpt和itl的模型数据，也就是表名获取对应字段然后产生新的mapper对象
//        ArrayList<DBModel> models_rpt = (ArrayList<DBModel>) importDBModelToClass("./src/test/resources/（数仓模型）RPT层.xlsx");
        ArrayList<DBModel> models_rpt = (ArrayList<DBModel>) importDBModelToClass(filePath);
        //获取到的mapper对象
        ArrayList<Mapper> mapperSet = getMapperSet(models_rpt, names_rpt);

        //获取到没有映射到的表名数据
        Set<String> rptUnmapped = models_rpt.stream().map(DBModel::get表名).collect(Collectors.toSet());
        List<String> unMapped_rpt = names_rpt.stream().filter(x -> !rptUnmapped.contains(x.toUpperCase())).collect(Collectors.toList());
        ArrayList<Mapper> mapperSetUnMapped = (ArrayList<Mapper>) unMapped_rpt.stream()
                .map(x -> new Mapper().setGroup("UnMapped").setTARGET_TABLE(x.toUpperCase()))
                .collect(Collectors.toList());

        //写入新的excel
        Map<String, String> map = new LinkedHashMap<>();
        Class clazz = Class.forName("com.myccb.Entity.mapper.Mapper");
        Field[] fieldsArr = clazz.getDeclaredFields();
        for (int i = 0; i < fieldsArr.length; i++) {
            map.put(fieldsArr[i].getName(), fieldsArr[i].getName());
        }
        ArrayList<Mapper> dataset = new ArrayList<>();
        dataset.addAll(mapperSet);
        dataset.addAll(mapperSetUnMapped);

        String filePath1 = new File("").getAbsolutePath() + "\\out\\mapper" + "\\" + "rptMapper.xls";
        File f = new File(filePath1);
        makeDirRecurse(f.getParentFile());
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.err.println("目标文件路径有误，请确认输出文件路径");
            }
        }
        OutputStream out = new FileOutputStream(f);
        ExcelUtil.exportExcel(map,dataset,out,null,"RPT");
        out.close();
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
     * @param models_itl_rpt  数仓模型实体集合
     * @param names_itl_rpt  目标文件表名集合
     * @return ArrayList<Mapper>
     * @Description 获取新的mapper对象
     * @author zj
     * @since 2020/4/2 09:40
     */
    private ArrayList<Mapper> getMapperSet(ArrayList<DBModel> models_itl_rpt, ArrayList<String> names_itl_rpt) {
        ArrayList<Mapper> mapperSet = (ArrayList<Mapper>) models_itl_rpt.stream()
                .filter(x -> names_itl_rpt.contains(x.get表名().toUpperCase()))
                .filter(x -> x.get表名().toUpperCase().startsWith("T") ? !x.get列名().equals("ETL_DT") : !x.get列名().equals("STAT_DT"))
                .map(x -> new Mapper()
                        .setGroup("Group1")
                        .setLOGICAL_ENTITY(x.get表名备注().split("\\s+")[0])
                        .setTARGET_TABLE(x.get表名())
                        .setTARGET_FIELD(x.get列名())
                        .setLOGICAL_ATTRIBUTE(x.get列名备注())
                        .setTARGET_DATA_TYPE(DataTypeSwitcher(x.get数据类型()))
                        .setBLANK_COLUMN(" ")
                        .setSTAGE_TABLE("ET_" + x.get表名())
                        .setSTAGE_FIELD(x.get列名())
                        .set源字段描述(x.get列名备注())
                        .setSOURCE_DATA_TYPE(DataTypeSwitcher(x.get数据类型()))
                ).collect(Collectors.toList());
        return mapperSet;
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
     * @param filePath 文件路径
     * @return java.util.Collection<com.myccb.Entity.mapper.DBModel1>
     * @Description 获取数仓模型数据
     * @author Swagger-Ranger
     * @since 2020/2/24 18:33
     */
    private Collection<DBModel> importDBModelToClass( String filePath ) throws FileNotFoundException {

        File f = new File(filePath);
        InputStream in = new FileInputStream(f);

        ExcelLogs logs = new ExcelLogs();
        Class clazz = null;
        try {
            clazz = Class.forName("com.myccb.Entity.mapper.DBModel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Collection<DBModel> tables = ExcelUtil.importSheetCellToClass(clazz, in, "0", 1, logs);
        return tables;
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


}
