package com.myccb.Generator;

import com.myccb.Entity.Append;
import com.myccb.Entity.F2;
import com.myccb.Entity.F5;
import com.myccb.Entity.mapper.DBModel1;
import com.myccb.util.ExcelUtil.ExcelLogs;
import com.myccb.util.ExcelUtil.ExcelSheet;
import com.myccb.util.ExcelUtil.ExcelUtil;
import com.myccb.util.StringUtil;

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


    /**
     * @param filePath  数仓模型文件路径
     * @param sheetName 页签名
     * @Description 根据传入路径自动生成Mapper.xls文件
     * @author zj
     * @since 2020/4/01 09:40
     */
    public void generateMapper(String filePath, String sheetName) throws IOException, ClassNotFoundException {
        if ("".equals(StringUtil.trimStr(filePath))) throw new RuntimeException("请输入源文件的文件路径");
        //根据表名分别获取rpt和itl的模型数据，也就是表名获取对应字段然后产生新的mapper对象
        ArrayList<DBModel1> models_itl = (ArrayList<DBModel1>) importDBModelToClass(filePath, sheetName);
        ArrayList<DBModel1> appendMapper = (ArrayList<DBModel1>) models_itl.stream().filter(x -> "Append".equals(x.get算法())).collect(Collectors.toList());
        ArrayList<DBModel1> f2Mapper = (ArrayList<DBModel1>) models_itl.stream().filter(x -> "F2".equals(x.get算法())).collect(Collectors.toList());
        ArrayList<DBModel1> f5Mapper = (ArrayList<DBModel1>) models_itl.stream().filter(x -> "F5".equals(x.get算法())).collect(Collectors.toList());
        ExcelSheet<Append> appendExcelSheet = null;
        ExcelSheet<F2> f2ExcelSheet = null;
        ExcelSheet<F5> f5ExcelSheet = null;
        //生成算法为Append对应的mapper
        if (appendMapper != null && appendMapper.size() != 0){
            //获取到的mapper对象
            ArrayList<Append> mapperSet = getAppendSet(appendMapper, sheetName);
            Map<String, String> appendMap = new LinkedHashMap<>();
            //写入新的excel
            Class clazz = Class.forName("com.myccb.Entity.Append");
            Field[] fieldsArr = clazz.getDeclaredFields();
            for (int i = 0; i < fieldsArr.length; i++) {
                appendMap.put(fieldsArr[i].getName(), fieldsArr[i].getName());
            }
            appendExcelSheet = new ExcelSheet<>();
            appendExcelSheet.setSheetName("Append");
            appendExcelSheet.setHeaders(appendMap);
            appendExcelSheet.setDataset(mapperSet);
        }
        //生成算法为F2对应的mapper
        if (f2Mapper != null && f2Mapper.size() != 0){
            //获取到的mapper对象
            ArrayList<F2> mapperSet = getF2Set(f2Mapper, sheetName);
            Map<String, String> f2Map = new LinkedHashMap<>();
            //写入新的excel
            Class clazz = Class.forName("com.myccb.Entity.F2");
            Field[] fieldsArr = clazz.getDeclaredFields();
            for (int i = 0; i < fieldsArr.length; i++) {
                f2Map.put(fieldsArr[i].getName(), fieldsArr[i].getName());
            }
            f2ExcelSheet = new ExcelSheet<>();
            f2ExcelSheet.setSheetName("F2");
            f2ExcelSheet.setHeaders(f2Map);
            f2ExcelSheet.setDataset(mapperSet);
        }
        //生成算法为F5对应的mapper
        if (f5Mapper != null && f5Mapper.size() != 0){
            //获取到的mapper对象
            ArrayList<F5> mapperSet = getF5Set(f5Mapper, sheetName);
            Map<String, String> f5Map = new LinkedHashMap<>();
            //写入新的excel
            Class clazz = Class.forName("com.myccb.Entity.F5");
            Field[] fieldsArr = clazz.getDeclaredFields();
            for (int i = 0; i < fieldsArr.length; i++) {
                f5Map.put(fieldsArr[i].getName(), fieldsArr[i].getName());
            }
            f5ExcelSheet = new ExcelSheet<>();
            f5ExcelSheet.setSheetName("F5");
            f5ExcelSheet.setHeaders(f5Map);
            f5ExcelSheet.setDataset(mapperSet);
        }

        String filePath1 = new File("").getAbsolutePath() + "\\out\\" + sheetName + "\\mapper\\" + sheetName + "Mapper.xls";
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
        ExcelUtil.exportExcel(appendExcelSheet, f2ExcelSheet, f5ExcelSheet, out, null);
        out.close();
    }


    /**
     * @param models_itl_rpt  数仓模型实体集合
     * @return ArrayList<Append>
     * @Description 获取新的mapper对象
     * @author zj
     * @since 2020/4/2 09:40
     */
    private ArrayList<Append> getAppendSet(ArrayList<DBModel1> models_itl_rpt, String sheetName) {
        ArrayList<Append> mapperSet = (ArrayList<Append>) models_itl_rpt.stream()
                .filter(x -> "是".equals(x.get是否有效()))
                .filter(x -> "ITL".equals(sheetName) ? !x.get列名().equals("ETL_DT") : !x.get列名().equals("STAT_DT"))
                .map(x -> new Append()
                        .setGroup("Group1")
                        .setLOGICAL_ENTITY(x.get表名备注().split("\\s+")[0])
                        .setTARGET_TABLE(x.get表名())
                        .setTARGETFIELD(x.get列名())
                        .setLOGICAL_ATTRIBUTE(x.get列名备注())
                        .setTARGET_DATA_TYPE(DataTypeSwitcher(x.get数据类型()))
                        .setBLANK_LINE(" ")
                        .setSTAGE_TABLE("ET_" + x.get表名())
                        .setSTAGE_FIELD(x.get列名())
                        .set源字段描述(x.get列名备注())
                        .setSOURCE_DATA_TYPE(DataTypeSwitcher(x.get数据类型()))
                ).collect(Collectors.toList());
        return mapperSet;
    }

    /**
     * @param models_itl_rpt  数仓模型实体集合
     * @return ArrayList<F2>
     * @Description 获取新的mapper对象
     * @author zj
     * @since 2020/4/2 09:40
     */
    private ArrayList<F2> getF2Set(ArrayList<DBModel1> models_itl_rpt, String sheetName) {
        ArrayList<F2> mapperSet = (ArrayList<F2>) models_itl_rpt.stream()
                .filter(x -> "是".equals(x.get是否有效()))
                .filter(x -> "ITL".equals(sheetName) ? !x.get列名().equals("ETL_DT") : !x.get列名().equals("STAT_DT"))
                .map(x -> new F2()
                        .setTemp_Table(x.get表名())
                        .setPrimary_Key("")
                        .setNeed_Update("")
                        .setScript("")
                        .setTable_Name(x.get表名())
                        .setOwner("")
                ).collect(Collectors.toList());
        return mapperSet;
    }

    /**
     * @param models_itl_rpt  数仓模型实体集合
     * @return ArrayList<F5>
     * @Description 获取新的mapper对象
     * @author zj
     * @since 2020/4/2 09:40
     */
    private ArrayList<F5> getF5Set(ArrayList<DBModel1> models_itl_rpt, String sheetName) {
        ArrayList<F5> mapperSet = (ArrayList<F5>) models_itl_rpt.stream()
                .filter(x -> "是".equals(x.get是否有效()))
                .filter(x -> "ITL".equals(sheetName) ? !x.get列名().equals("ETL_DT") : !x.get列名().equals("STAT_DT"))
                .map(x -> new F5()
                        .setTemp_Table(x.get表名())
                        .setPrimary_Key("")
                        .setNeed_Update("")
                        .setScript("")
                        .setTable_Name(x.get表名())
                        .setOwner("")
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
     * @param sheetName 页签名
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


    public static void main( String[] args ) throws IOException, ClassNotFoundException {
        new NewMapperGenerator().generateMapper("E:\\MYSH\\needExcel\\数仓模型.xlsx", "ITL");
    }

}
