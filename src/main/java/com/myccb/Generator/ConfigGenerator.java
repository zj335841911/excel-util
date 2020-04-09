package com.myccb.Generator;

import com.myccb.Entity.ConfigModel;
import com.myccb.Entity.mapper.DBModel1;
import com.myccb.Entity.mapper.ITL_RPT;
import com.myccb.util.ExcelUtil.ExcelLogs;
import com.myccb.util.ExcelUtil.ExcelUtil;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 生成sqoop配置文件的工具类
 * @author zj
 * @version 1.0
 * @date 2020/03/31 13:34
 */
public class ConfigGenerator {

    public void configGenerator(String itlFilePath, String rptFilePath, String targetFilePath ) throws FileNotFoundException, ClassNotFoundException {
        //获取目标表名数据集合
        ArrayList<String> names_itl = getSourceTables(targetFilePath, "itl");
        ArrayList<String> names_rpt = getSourceTables(targetFilePath, "rpt");
        //根据给定的路径读取excel生成相应的数据仓模型数据
        Collection<DBModel1> itlTables = importDBModel1ToClass(itlFilePath);
        Collection<DBModel1> rptTables = importDBModel1ToClass(rptFilePath);
        //用于存储根据表名分类后的数据
        Map<String, List<DBModel1>> itlContents = null;
        Map<String, List<DBModel1>> rptContents = null;
        //提取表中表名与目标表表名相同的数据，并根据表名对集合数据进行分类
        itlContents = itlTables.stream()
                .filter(x -> names_itl.contains(x.get表名().toUpperCase()))
                .collect(Collectors.groupingBy(DBModel1::get表名));
        rptContents = rptTables.stream()
                .filter(x -> names_rpt.contains(x.get表名().toUpperCase()))
                .collect(Collectors.groupingBy(DBModel1::get表名));
        List<ConfigModel> configModels = new ArrayList<>();
        itlContents.entrySet().forEach(
                x -> {
                    List<DBModel1> dbModelList = x.getValue();
                    //获取配置文件实体集合
                    List<ConfigModel> configModels1 = getConfigModels1(dbModelList);
                    configModels.addAll(configModels1);
                }
        );
        //用于存储根据表名分类后的数据
        Map<String, List<ConfigModel>> configModel = null;
        //将数据根据表名分类
        configModel = configModels.stream().collect(Collectors.groupingBy(ConfigModel::getTable_name));
        //用于存储配置文件内容
        StringBuffer configTextTotal = new StringBuffer();
        //获取配置文件头部示例
        configTextTotal.append(getConfigTextTotalHead());
        //向配置文件内添加内容
        addContentToConfigTextTotal(configTextTotal, configModel);
        configModels.clear();
        rptContents.entrySet().forEach(
                x -> {
                    List<DBModel1> dbModelList = x.getValue();
                    //获取配置文件实体集合
                    List<ConfigModel> configModels1 = getConfigModels1(dbModelList);
                    configModels.addAll(configModels1);
                }
        );
        //将数据根据表名分类
        configModel = configModels.stream().collect(Collectors.groupingBy(ConfigModel::getTable_name));
        //向配置文件内添加内容
       addContentToConfigTextTotal(configTextTotal, configModel);

        //写入文件,文件路径为固定的相对路径
        String filePath1 = new File("").getAbsolutePath() + "\\out\\SqoopConfig" + "\\" + "itl_rpt_cfg.conf";
        write2File(filePath1, configTextTotal.toString());
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
     * @return java.util.Collection<com.myccb.Entity.mapper.DBModel1>
     * @Description 获取数仓模型数据
     * @author zj
     * @since 2020/3/24 18:33
     */
    private Collection<DBModel1> importDBModel1ToClass(String filePath ) throws FileNotFoundException {

        File f = new File(filePath);
        InputStream in = new FileInputStream(f);

        ExcelLogs logs = new ExcelLogs();
        Class clazz = null;
        try {
            clazz = Class.forName("com.myccb.Entity.mapper.DBModel1");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Collection<DBModel1> tables = ExcelUtil.importStringSheetToClass(clazz, in, "0", 1, logs);
        return tables;
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

    /**
     * @param dbModelList 数仓实体集合
     * @return ArrayList<ConfigModel>
     * @Description 获取配置文件实体集合
     * @author zj
     * @since 2020/4/2 19:01
     */
    private ArrayList<ConfigModel> getConfigModels1(List<DBModel1> dbModelList) {
        ArrayList<ConfigModel> configModels1 = new ArrayList<>();
        configModels1 = (ArrayList<ConfigModel>) dbModelList.stream()
                .map(y -> new ConfigModel()
                        .setConfig_table_name(y.get表名().startsWith("T")?"itl_tab_col_conf":"rpt_tab_col_conf")
                        .setTable_name(y.get表名())
                        .setSrc_sys_short_name(y.get表名().startsWith("T")?"itl":"rpt")
                        .setTable_comments(y.get表名备注())
                        .setColumn_id(y.getCOLUMN_ID().intValue())
                        .setColumn_name(y.get列名())
                        .setColumn_comments(y.get列名备注())
                        .setColumn_data_type(y.get数据类型())
                        .setData_precision(y.get整数位())
                        .setData_scale(y.get小数位())
                        .setData_length(y.get字段长度())
                ).collect(Collectors.toList());
        return configModels1;
    }

    /**
     * @return StringBuffer
     * @Description 获取配置文件头部示例
     * @author zj
     * @since 2020/4/2 19:01
     */
    private StringBuffer getConfigTextTotalHead() {
        StringBuffer configTextTotalHead = new StringBuffer();
        configTextTotalHead.append("######################################\r\n");
        configTextTotalHead.append("# config format\r\n");
        configTextTotalHead.append("# [target table name]\r\n");
        configTextTotalHead.append("# SQOOP_MODE = xxxx          -- full or increment or reload\r\n");
        configTextTotalHead.append("# SOURCE_DB = xxxx           -- source db name ,must defined in sqoop_db.config\r\n");
        configTextTotalHead.append("# SOURCE_TABLE = xxxx        -- source table name\r\n");
        configTextTotalHead.append("# SOURCE_COLUMNS = xxxx      -- source table column list need to export,concat by \",\"\r\n");
        configTextTotalHead.append("# INCREMENT_FILED = xxxx     -- increment control filed\r\n");
        configTextTotalHead.append("# INCREMENT_INTERVAL = xxxx  -- increment interval,must be integet,just for reload mode\r\n");
        configTextTotalHead.append("# MAPS_NUM = xxxx            -- maps processes number\r\n");
        configTextTotalHead.append("# TARGET_DIR = xxxx          -- export file directory,start with  \"wasbs://\"\r\n");
        configTextTotalHead.append("# ENABLE = xxxx              -- 1 or 0\r\n");
        configTextTotalHead.append("######################################\r\n\r\n");
        return configTextTotalHead;
    }

    /**
     *
     * @param configType
     * @param column_name 处理后的字段名
     * @param columnName 未经处理的的字段名
     * @param columnDateType 字段类型
     * @param dateLength 字段长度
     * @return StringBuffer
     * @Description 获取source_columns
     * @author zj
     * @since 2020/4/2 19:01
     */
    private StringBuffer getSourceColumns(String configType, String column_name, String columnName, String columnDateType, String dateLength) {
        StringBuffer source_columns = new StringBuffer();
        if ("itl".equals(configType) ? "ETL_DT".equals(columnName) : "STAT_DT".equals(columnName)){
            source_columns.append("");
        }else if ("DATE".equals(columnDateType)){
            source_columns.append("TO_CHAR(" + column_name + ",\"YYYY-MM-DD HH24:MI:SS\") " + columnName + ",");
        }else if (columnDateType.startsWith("TIMESTAMP")){
            source_columns.append("TO_CHAR(" + column_name + ",\"YYYY-MM-DD HH24:MI:SS\") " + columnName + ",");
        }else if ("NUMBER".equals(columnDateType)){
            source_columns.append("CAST(" + column_name + " AS DECIMAL(" + dateLength + ")) " + columnName + ",");
        }else if (!columnName.equals(column_name)){
            source_columns.append(column_name + " " + columnName + ",");
        }else {
            source_columns.append(columnName + ",");
        }
        return source_columns;
    }

    /**
     * @param configTextTotal 配置文件
     * @param configModel 配置实体集合
     * @return StringBuffer
     * @Description 向配置文件内添加内容
     * @author zj
     * @since 2020/4/2 19:01
     */
    private void addContentToConfigTextTotal(StringBuffer configTextTotal, Map<String, List<ConfigModel>> configModel) {
        configModel.entrySet().forEach(
                x -> {
                    StringBuffer configText = new StringBuffer();
                    //文件名
                    String targertTableName = x.getKey();
                    //配置文件类型
                    String configType = x.getValue().get(0).getSrc_sys_short_name();
                    //根据表名分类后的每个表所对应的内容集合
                    List<ConfigModel> configModelList = x.getValue();
                    //获取configText
                    configText.append(getConfigText(targertTableName, configType, configModelList));
                    //配置文件内容拼接
                    configTextTotal.append(configText);
                }
        );
    }


    /**
     * @param targertTableName 文件名
     * @param configType 配置文件类型
     * @param configModelList 根据表名分类后的每个表所对应的内容集合
     * @return StringBuffer
     * @Description 获取configText
     * @author zj
     * @since 2020/4/2 19:01
     */
    private StringBuffer getConfigText(String targertTableName, String configType, List<ConfigModel> configModelList) {
        StringBuffer configText = new StringBuffer();
        configText.append("############################ "+ configType.toUpperCase() + " SYSTEM\r\n");
        configText.append("[" + targertTableName + "]\r\n");
        configText.append("SQOOP_MODE         = increment\r\n");
        configText.append("SOURCE_DB          = " + configType + "\r\n");
        configText.append("SOURCE_TABLE       = " + targertTableName + "\r\n");
        StringBuffer to_ora_columns = new StringBuffer();
        StringBuffer source_columns = new StringBuffer();
        configModelList.stream().sorted(Comparator.comparing(ConfigModel::getColumn_id)).forEach(
                y -> {
                    to_ora_columns.append(y.getColumn_name() + ",");
                    String column_name = "";
                    if (y.getColumn_name().length() > 4 &&"re__".equals(y.getColumn_name().substring(0, 4))){
                        column_name = y.getColumn_name().substring(4);
                    }
                    else {
                        column_name = y.getColumn_name();
                    }
                    String columnName = y.getColumn_name();
                    String columnDateType = y.getColumn_data_type();
                    String dateLength = y.getData_length();
                    //获取source_columns
                    source_columns.append(getSourceColumns(configType, column_name, columnName, columnDateType, dateLength));
                }
        );
        configText.append("TO_ORA_COLUMNS     = " + to_ora_columns.substring(0, to_ora_columns.length()-1) + "\r\n");
        configText.append("SOURCE_COLUMNS     = " + source_columns.substring(0, source_columns.length()-1) + "\r\n");
        if ("itl".equals(configType)){
            configText.append("INCREMENT_FILED    = etl_dt\r\n");
        }else if ("rpt".equals(configType)){
            configText.append("INCREMENT_FILED    = stat_dt\r\n");
        }
        configText.append("INCREMENT_INTERVAL = 0\r\n");
        configText.append("MAPS_NUM           = 1\r\n");
        if ("itl".equals(configType)){
            configText.append("TARGET_DIR         = /user/hive/warehouse/itl.db\r\n");
        }else if ("rpt".equals(configType)){
            configText.append("TARGET_DIR         = /user/hive/warehouse/rpt.db\r\n");
        }
        configText.append("ENABLE             = 1\r\n\r\n");
        return configText;
    }

    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
        ConfigGenerator cg = new ConfigGenerator();
        cg.configGenerator("./src/test/resources/（数仓模型）ITL层.xlsx",
                "./src/test/resources/（数仓模型）RPT层.xlsx",
                "./src/test/resources/ITL_RPT_日数据量统计_20200119(1).xlsx"
        );
    }

}
