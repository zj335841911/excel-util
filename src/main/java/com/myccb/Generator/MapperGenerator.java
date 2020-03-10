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

public class MapperGenerator {

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
        new MapperGenerator().generateMapper();
    }

    public void generateMapper() throws IOException, ClassNotFoundException {
        //获取表名数据集合
        ArrayList<String> names_itl = getSourceTables("./src/test/resources/ITL_RPT_日数据量统计_20200119(1).xlsx", "itl");
        ArrayList<String> names_rpt = getSourceTables("./src/test/resources/ITL_RPT_日数据量统计_20200119(1).xlsx", "rpt");

        //根据表名分别获取rpt和itl的模型数据，也就是表名获取对应字段然后产生新的mapper对象
        ArrayList<DBModel> models_itl = (ArrayList<DBModel>) importDBModelToClass("./src/test/resources/（数仓模型）ITL层.xlsx");
        ArrayList<DBModel> models_rpt = (ArrayList<DBModel>) importDBModelToClass("./src/test/resources/（数仓模型）RPT层.xlsx");

        //获取到的mapper对象
        models_itl.addAll(models_rpt);
        ArrayList<Mapper> mapperSet = (ArrayList<Mapper>) models_itl.stream()
                .filter(x -> names_rpt.contains(x.get表名().toUpperCase()) || names_itl.contains(x.get表名().toUpperCase()))
                .filter(x -> !x.get列名().equals("ETL_DT"))
                .map(x -> new Mapper()
                        .setGroup("Group1")
                        .setLOGICAL_ENTITY(x.get表名备注().split("\\s+")[0])
                        .setTARGET_TABLE(x.get表名())
                        .setTARGET_FIELD(x.get列名())
                        .setLOGICAL_ATTRIBUTE(x.get列名备注())
                        .setTARGET_DATA_TYPE(DataTypeSwitcher(x.get数据类型()))
                        .setBLANK_COLUMN(" ")
                        .setSTAGE_TABLE("E_" + x.get表名())
                        .setSTAGE_FIELD(x.get列名())
                        .set源字段描述(x.get列名备注())
                        .setSOURCE_DATA_TYPE(DataTypeSwitcher(x.get数据类型()))
                ).collect(Collectors.toList());

        //获取到没有映射到的表名数据
        Set<String> itlUnmapped = models_itl.stream().map(DBModel::get表名).collect(Collectors.toSet());
        Set<String> rptUnmapped = models_rpt.stream().map(DBModel::get表名).collect(Collectors.toSet());
        List<String> unMapped_itl = names_itl.stream().filter(x -> !itlUnmapped.contains(x.toUpperCase())).collect(Collectors.toList());
        List<String> unMapped_rpt = names_rpt.stream().filter(x -> !rptUnmapped.contains(x.toUpperCase())).collect(Collectors.toList());
        unMapped_itl.addAll(unMapped_rpt);

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

        File f = new File("mapper_v2.1.xls");
        OutputStream out = new FileOutputStream(f);
        ExcelUtil.exportExcel(map, dataset, out);
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
     * @return java.util.Collection<com.myccb.Entity.mapper.DBModel>
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
        Collection<DBModel> tables = ExcelUtil.importStringSheetToClass(clazz, in, "0", 1, logs);
        return tables;
    }


}
