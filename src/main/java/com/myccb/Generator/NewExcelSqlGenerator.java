package com.myccb.Generator;

import com.myccb.Entity.*;
import com.myccb.Entity.mapper.Mapper;
import com.myccb.util.ExcelUtil.ExcelLogs;
import com.myccb.util.ExcelUtil.ExcelUtil;
import com.myccb.util.StringUtil;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * @Description 生成个性化代码的工具类
 * @Author zj
 * @Date 2020/04/01 09:46
 */

public class NewExcelSqlGenerator {

    private static final String SqlTitle = "" +
            "-- ***************************************************************************************************\n" +
            "--  **  文件名称: {0}.sql \n" +
            "--  **  所属主题: {1}\n" +
            "--  **  功能描述: {2}\n" +
            "--  **    创建者: {3}\n" +
            "--  **  创建日期: {4}\n" +
            "--  **  修改日志: \n" +
            "--  **  修改日期 \t\t修改人 \t\t修改内容 \n" +
            "--  ** -----------------------------------------------------------------------------------------------\n" +
            "--  **\n" +
            "--  ** -----------------------------------------------------------------------------------------------\n" +
            "--  **  Copyright (c) 2020 MIANYANG CITY COMMERCIAL BANK.\n" +
            "--  **  All Rights Reserved.\n" +
            "-- ***************************************************************************************************\n" +
            "\n";


    /**
     * @param srcFilePath 文件路径
     * @param subject     输出文件的注释头
     * @param description 输出文件的注释头
     * @param creator     输出文件的注释头
     * @Description 将文本数据按照固定格式写入文件
     * @author zj
     * @since 2020/04/01 09:46
     */
    public void generate( String srcFilePath, String sheetName, int sqlIndex, String subject, String description, String creator ) throws FileNotFoundException {

        if (StringUtil.trimStr(srcFilePath).equals("")) throw new RuntimeException("请输入源文件的文件路径");

        File f = new File(srcFilePath);
        InputStream in = new FileInputStream(f);

        ExcelLogs logs = new ExcelLogs();
        String clazzPath = "com.myccb.Entity" + "." + sheetName;
        Class clazz = null;
        try {
            clazz = Class.forName(clazzPath);
        } catch (ClassNotFoundException e) {
            System.err.println("请确认输入，待获取SQL文本的sheet名是否正确");

        }

        Map<String, String> contents = new HashMap<>();
        String LN = "\r\n\r\n";
        String s4 = "";String s13 = "";String s14 = "";String s20 = "";String s25 = "";String s26 = "";
        String s31 = "";String s33 = "";String s34 = "";String s35 = "";String s36 = "";String s37 = "";
        String s38 = "";String s39 = "";String s40 = "";String s41 = "";String s42 = "";String s43 = "";
        String s44 = "";String s45 = "";String s46 = "";String s53 = "";String s54 = "";

        //针对不同的sheet作不同的解析
        if ("Append".equals(sheetName)) {
            //导入sheet中特定的列的属性为Java对象
            Collection<Append> dataSrc = ExcelUtil.importSheetCellToClass(clazz, in, sheetName, sqlIndex, logs);
            //将对象转化为list
            ArrayList<Append> appends = (ArrayList<Append>) dataSrc.stream().collect(Collectors.toList());
            //提取对象中的group和target_table，合并后作为唯一标识进行去重
            List<Append> appendArrayList = appends.stream().collect(
                    Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(
                                    Comparator.comparing(o ->
                                            o.getGroup() + ";" + o.getTARGET_TABLE()))),
                            ArrayList::new));
            String[] arr1 = new String[appends.size() + 1];
            int j = 0;
            int x = 1;
            int a = 0;
            for (int y = 1; y <= appendArrayList.size(); y++){
                while(appends.get(x).getGroup().equals(appends.get(x-1).getGroup())
                        && appends.get(x).getTARGET_TABLE().equals(appends.get(x-1).getTARGET_TABLE())){
                    j = j + 1;
                    x = x + 1;
                    if (x == appends.size()){
                        break;
                    }
                }
                StringBuffer sqlContents = new StringBuffer();
                sqlContents.append("--当天数据插入目标表" + LN + "insert overwrite table "
                        + appends.get(a).getTARGET_TABLE() + " partition(pt)" + LN);
                for (int Z = a; Z <= j; Z++ ){
                    //目标字段
                    String TARGETFIELD = appends.get(Z).getTARGETFIELD();
                    //源字段
                    String STAGE_FIELD = appends.get(Z).getSTAGE_FIELD();
                    //补充字段
                    String LOGIC = appends.get(Z).getLOGIC();
                    //获取源字段映射成目标字段的语句
                    arr1[Z] = getArr(Z, a, TARGETFIELD, STAGE_FIELD, LOGIC);
                    sqlContents.append(arr1[Z]);
                }
                sqlContents.append(",getdate() as Create_Time" + LN + ",to_date('$bizdate','yyyymmdd') as Tx_Dt" + LN
                        + "from " + appends.get(a).getSTAGE_TABLE() + LN + "where pt=concat('$bizdate','000000');" + LN);
                contents.put(appends.get(a).getGroup()+"_"+appends.get(a).getTARGET_TABLE(),sqlContents.toString());
                a = x;
                x = x + 1;
                j = j + 1;
                s4 = "";
            }
        }

        if ("F2".equals(sheetName)) {
            //导入sheet中特定的列的属性为Java对象
            Collection<F2> dataSrc = ExcelUtil.importSheetCellToClass(clazz, in, sheetName, sqlIndex, logs);
            //将对象转化为list
            ArrayList<F2> f2s = (ArrayList<F2>) dataSrc.stream().collect(Collectors.toList());
            int Z = 0;
            while (!StringUtil.isNull(f2s.get(Z).getTable_Name())){
                String[] arr3 = new String[100];
                String[] arr4 = new String[100];
                String[] arr5 = new String[100];
                String[] prikey = null;
                String[] upd = null;
                StringBuffer sqlContents = new StringBuffer();
                String str = "";
                sqlContents.append("--新旧数据比对 (新增的和变化的使用新数据,删除的保持不变)" + LN
                        + "insert overwrite table T_" + f2s.get(Z).getTable_Name() + "_01" + LN + "select" + LN);
                prikey = f2s.get(Z).getPrimary_Key().split(",");
                int j = prikey.length;
                for (int i = 0; i < j; i++){
                    if (i == 0){
                        arr3[i] = "case when b." + prikey[0] + " is null then a." + prikey[i] + " else b." + prikey[i] + " end" + LN;
                        arr4[i] = "on a." + prikey[i] + "=b." + prikey[i] + "" + LN;
                    }else {
                        arr3[i] = ",case when b." + prikey[0] + " is null then a." + prikey[i] + " else b." + prikey[i] + " end" + LN;
                        arr4[i] = "and a." + prikey[i] + "=b." + prikey[i] + "" + LN;
                    }
                    sqlContents.append(arr3[i]);
                    str = str + arr4[i];
                }
                upd = f2s.get(Z).getNeed_Update().split(",");
                int y = upd.length;
                for (int x = 0; x < y; x++){
                    arr5[x] = ",case when b." + prikey[0] + " is null then a." + upd[x] + " else b." + upd[x] + " end" + LN;
                    sqlContents.append(arr5[x]);
                }
                sqlContents.append(",coalesce(a.create_time,b.create_time)" + LN + ",case when a." + prikey[0]
                        + " is null then to_date('$bizdate','yyyymmdd') else A.Tx_Dt end" + LN + "from "
                        + f2s.get(Z).getTable_Name() + " a" + LN + "full outer join T_" + f2s.get(Z).getTable_Name()
                        + "_CUR_I b" + LN + str + ";" + LN + "--插入目标表" + LN + "insert overwrite table "
                        + f2s.get(Z).getTable_Name() + " select * from T_" + f2s.get(Z).getTable_Name() + "_01;" + LN);
                contents.put(f2s.get(Z).getTable_Name(),sqlContents.toString());
                String temp_table = "Create table T_" + StringUtil.trimStr(f2s.get(Z).getTable_Name()) + "_CUR_I" + " like "
                        + StringUtil.trimStr(f2s.get(Z).getTable_Name()) + ";" + LN + "Create table T_"
                        + StringUtil.trimStr(f2s.get(Z).getTable_Name()) + "_01" + " like "
                        + StringUtil.trimStr(f2s.get(Z).getTable_Name()) + ";" + LN;
                f2s.get(Z).setTemp_Table(temp_table);
                Z = Z + 1;
                if (Z == f2s.size()){
                    break;
                }
            }
        }

        if ("F5".equals(sheetName)) {
            //导入sheet中特定的列的属性为Java对象
            Collection<F5> dataSrc = ExcelUtil.importSheetCellToClass(clazz, in, sheetName, sqlIndex, logs);
            //将对象转化为list
            ArrayList<F5> f5s = (ArrayList<F5>) dataSrc.stream().collect(Collectors.toList());
            int Z = 0;
            while (!StringUtil.isNull(f5s.get(Z).getPrimary_Key())){
                String[] arr1 = new String[10];
                String[] arr2 = new String[20];
                String[] arr3 = new String[10];
                String[] arr4 = new String[20];
                String[] arr5 = new String[10];
                String[] arr6 = new String[20];
                String[] arr7 = new String[10];
                String[] arr8 = new String[20];
                String[] arr9 = new String[10];
                String[] arr10 = new String[20];
                String[] arr12 = new String[20];
                String[] prikey = null;
                String[] upd = null;
                StringBuffer sqlContents = new StringBuffer();
                sqlContents.append("--避免同一天多次运行造成数据重复 " + LN + "insert overwrite table T_"
                        + f5s.get(Z).getTable_Name() + "_01" + LN + "select" + LN);
                prikey = f5s.get(Z).getPrimary_Key().split(",");
                int j = prikey.length;
                for (int i = 0; i < j; i++){
                    if (i == 0){
                        arr1[i] = prikey[i] + LN;
                        arr3[i] = "case when b." + prikey[0] + " is null then a." + prikey[i] + " when a." + prikey[0]
                                + " is null then b." + prikey[i] + " end" + LN;
                        arr5[i] = "on a." + prikey[i] + "=b." + prikey[i] + "" + LN;
                        arr7[i] = "b." + prikey[i] + LN;
                        arr9[i] = "a." + prikey[i] + LN;
                    }else {
                        arr1[i] = "," + prikey[i] + LN;
                        arr3[i] = ",case when b." + prikey[0] + " is null then a." + prikey[i] + " when a." + prikey[0]
                                + " is null then b." + prikey[i] + " end" + LN;
                        arr5[i] = "and a." + prikey[i] + "=b." + prikey[i] + "" + LN;
                        arr7[i] = ",b." + prikey[i] + LN;
                        arr9[i] = ",a." + prikey[i] + LN;
                    }
                    sqlContents.append(arr1[i]);
                    s13 = s13 + arr3[i];
                    s20 = s20 + arr5[i];
                    s25 = s25 + arr7[i];
                    s41 = s41 + arr9[i];
                }
                sqlContents.append(",Start_Dt" + LN);
                if (!StringUtil.isNull(f5s.get(Z).getNeed_Update())) {
                    upd = f5s.get(Z).getNeed_Update().split(",");
                    int y = upd.length;
                    for (int x = 0; x < y; x++) {
                        arr2[x] = "," + upd[x] + LN;
                        arr4[x] = ",case when b." + prikey[0] + " is null then a." + upd[x] + " when a." + prikey[0]
                                + " is null then b." + upd[x] + " end" + LN;
                        arr6[x] = ",b." + upd[x] + LN;
                        arr12[x] = ",a." + upd[x] + LN;
                        if (x == 0) {
                            arr8[x] = "and coalesce(a." + upd[x] + ",'')=coalesce(b." + upd[x] + ",'')" + LN;
                            if (y > 1) {
                                arr10[x] = "where (coalesce(a." + upd[x] + ",'')<>coalesce(b." + upd[x] + ",'')" + LN;
                            } else {
                                arr10[x] = "where (coalesce(a." + upd[x] + ",'')<>coalesce(b." + upd[x] + ",''))" + LN;
                            }
                        } else if (x == y) {
                            arr10[x] = "or coalesce(a." + upd[x] + ",'')<>coalesce(b." + upd[x] + ",''))" + LN;
                            arr8[x] = "and coalesce(a." + upd[x] + ",'')=coalesce(b." + upd[x] + ",'')" + LN;
                        } else {
                            arr8[x] = "and coalesce(a." + upd[x] + ",'')=coalesce(b." + upd[x] + ",'')" + LN;
                            arr10[x] = "or coalesce(a." + upd[x] + ",'')<>coalesce(b." + upd[x] + ",'')" + LN;
                        }
                        sqlContents.append(arr2[x]);
                        s14 = s14 + arr4[x];
                        s26 = s26 + arr6[x];
                        s31 = s31 + arr8[x];
                        s38 = s38 + arr10[x];
                        s42 = s42 + arr12[x];
                    }
                }
                sqlContents.append(",case when End_Dt=to_date('$bizdate','yyyymmdd') then to_date('29990101','yyyymmdd') else End_Dt end" + LN
                        + ",Create_Time" + LN
                        + ",Tx_Dt" + LN
                        + "from " + f5s.get(Z).getTable_Name() + LN
                        + "where Start_Dt<>to_date('$bizdate','yyyymmdd');" + LN + LN
                        + "--新增、删除和变化的数据" + LN
                        + "insert overwrite table T_" + f5s.get(Z).getTable_Name() + "_INS" + LN
                        + "select" + LN
                        + s13
                        + ",case when b." + prikey[0] + " is null then a.Start_Dt when a." + prikey[0] + " is null then b.Start_Dt end" + LN
                        + s14
                        + ",case when b." + prikey[0] + " is null then to_date('29990101','yyyymmdd') when a." + prikey[0] + " is null then to_date('$bizdate','yyyymmdd') end" + LN
                        + ",case when b." + prikey[0] + " is null then a.Create_Time when a." + prikey[0] + " is null then b.Create_Time end" + LN
                        + ",case when b." + prikey[0] + " is null then a.Tx_Dt when a." + prikey[0] + " is null then b.Tx_Dt end" + LN
                        + "From T_" + f5s.get(Z).getTable_Name() + "_CUR_I a" + LN
                        + "full outer join (select * from T_" + f5s.get(Z).getTable_Name() + "_01 where End_Dt=to_date('29990101','yyyymmdd')) b" + LN
                        + s20);
                if (!StringUtil.isNull(f5s.get(Z).getNeed_Update())){
                    sqlContents.append(s31);
                }
                sqlContents.append("where (b." + prikey[0] + " is null or a." + prikey[0] + " is null);" + LN + LN
                        + "--没有变化的记录" + LN
                        + "insert overwrite table T_" + f5s.get(Z).getTable_Name() + "_NOCHG" + LN
                        + "select" + LN
                        + s25
                        + ",b.Start_Dt" + LN
                        + s26
                        + ",b.End_Dt" + LN
                        + ",b.Create_Time" + LN
                        + ",b.Tx_Dt" + LN
                        + "from T_" + f5s.get(Z).getTable_Name() + "_CUR_I a" + LN
                        + "join T_" + f5s.get(Z).getTable_Name() + "_01 b" + LN
                        + s20
                        + s31
                        + "and b.End_Dt=to_date('29990101','yyyymmdd');" + LN + LN
                        + "--插入结果表" + LN
                        + "insert overwrite table " + f5s.get(Z).getTable_Name() + LN
                        + "select * from (" + LN
                        + "select * from T_" + f5s.get(Z).getTable_Name() + "_01 where End_Dt<>to_date('29990101','yyyymmdd')" + LN
                        + "union all" + LN
                        + "select * from T_" + f5s.get(Z).getTable_Name() + "_INS" + LN
                        + "union all" + LN
                        + "select * from T_" + f5s.get(Z).getTable_Name() + "_NOCHG" + LN
                        + ") aaa" + LN
                        + ";" + LN);
                s33 = "--变化的数据原始记录置为失效" + LN;
                s34 = "INSERT overwrite table T_" + f5s.get(Z).getTable_Name() + "_CHG_01" + LN;
                s35 = ",to_date('$bizdate','yyyymmdd')" + LN;
                s36 = "FROM T_" + f5s.get(Z).getTable_Name() + "_CUR_I a" + LN;
                s37 = "join T_" + f5s.get(Z).getTable_Name() + "_01 b" + LN;
                s39 = "--变化的数据新增生效的记录" + LN;
                s40 = "INSERT overwrite table T_" + f5s.get(Z).getTable_Name() + "_CHG_02" + LN;
                s43 = ",a.Start_Dt" + LN;
                s44 = ",to_date('29990101','yyyymmdd')" + LN;
                s45 = ",a.Create_Time" + LN;
                s46 = ",a.Tx_Dt" + LN;
                s53 = "select * from T_" + f5s.get(Z).getTable_Name() + "_CHG_01" + LN;
                s54 = "select * from T_" + f5s.get(Z).getTable_Name() + "_CHG_02" + LN;
                contents.put(f5s.get(Z).getTable_Name(),sqlContents.toString());
                String temp_table = "Create table T_" + StringUtil.trimStr(f5s.get(Z).getTable_Name()) + "_CUR_I"
                            + " like " + StringUtil.trimStr(f5s.get(Z).getTable_Name()) + ";" + LN + "Create table T_"
                            + StringUtil.trimStr(f5s.get(Z).getTable_Name()) + "_01" + " like "
                            + StringUtil.trimStr(f5s.get(Z).getTable_Name()) + ";" + LN + "Create table T_"
                            + StringUtil.trimStr(f5s.get(Z).getTable_Name()) + "_INS" + " like "
                            + StringUtil.trimStr(f5s.get(Z).getTable_Name()) + ";" + LN + "Create table T_"
                            + StringUtil.trimStr(f5s.get(Z).getTable_Name()) + "_NOCHG" + " like "
                            + StringUtil.trimStr(f5s.get(Z).getTable_Name()) + ";";
                    f5s.get(Z).setTemp_Table(temp_table);
                    s13 = s14 = s20 = s25 = s26 = s31 = s33 = s34 = s35 = s36 = s37 = s38 = s39 = s40 = s41 = s42
                            = s43 = s44 = s45 = s46 = s53 = s54 ="";
                Z = Z + 1;
                if (Z == f5s.size()){
                    break;
                }
            }
        }

        if ("Map".equals(sheetName)) {
            //导入sheet中特定的列的属性为Java对象
            Collection<com.myccb.Entity.Map> dataSrc = ExcelUtil.importSheetCellToClass(clazz, in, sheetName, sqlIndex, logs);
            //将对象转化为list
            ArrayList<com.myccb.Entity.Map> maps = (ArrayList<com.myccb.Entity.Map>) dataSrc.stream().collect(Collectors.toList());
            //提取对象中的group和target_table，合并后作为唯一标识进行去重
            List<com.myccb.Entity.Map> mapArrayList = maps.stream().collect(
                    Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(
                                    Comparator.comparing(o ->
                                            o.getGroup() + ";" + o.getTARGET_TABLE()))),
                            ArrayList::new));
            String[] arr1 = new String[maps.size()+1];
            int j = 0;
            int x = 1;
            int a = 0;
            for (int y = 1; y <= mapArrayList.size(); y++){
                while(maps.get(x).getGroup().equals(maps.get(x-1).getGroup())
                        && maps.get(x).getTARGET_TABLE().equals(maps.get(x-1).getTARGET_TABLE())){
                    j = j + 1;
                    x = x + 1;
                    if (x == maps.size()){
                        break;
                    }
                }
                StringBuffer sqlContents = new StringBuffer();
                sqlContents.append("--当天数据插入目标表" + LN + "insert overwrite table T_"
                        + maps.get(a).getTARGET_TABLE() + "_CUR_I" + LN + "select" + LN);
                for (int Z = a; Z <= j; Z++ ){
                    //目标字段
                    String TARGETFIELD = maps.get(Z).getTARGETFIELD();
                    //源字段
                    String STAGE_FIELD = maps.get(Z).getSTAGE_FIELD();
                    //补充字段
                    String LOGIC = maps.get(Z).getLOGIC();
                    //获取源字段映射成目标字段的语句
                    arr1[Z] = getArr(Z, a, TARGETFIELD, STAGE_FIELD, LOGIC);
                    sqlContents.append(arr1[Z]);
                }
                sqlContents.append(",getdate() as Create_Time" + LN + ",to_date('$bizdate','yyyymmdd') as Tx_Dt" + LN
                        + "from " + maps.get(a).getSTAGE_TABLE() + LN + "where pt=concat('$bizdate','000000');" + LN);
                contents.put(maps.get(a).getGroup()+"_"+maps.get(a).getTARGET_TABLE(),sqlContents.toString());
                a = x;
                x = x + 1;
                j = j + 1;
                s4 = "";
            }
        }

        if ("ITL".equals(sheetName)) {
            //导入sheet中特定的列的属性为Java对象
            Collection<ITL> dataSrc = ExcelUtil.importSheetCellToClass(clazz, in, sheetName, sqlIndex, logs);
            //将对象转化为list
            ArrayList<ITL> itls = (ArrayList<ITL>) dataSrc.stream().collect(Collectors.toList());
            //提取对象中的group和target_table，合并后作为唯一标识进行去重
            List<ITL> itlArrayList = itls.stream().collect(
                    Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(
                                    Comparator.comparing(o ->
                                            o.getGroup() + ";" + o.getTARGET_TABLE()))),
                            ArrayList::new));
            String[] arr1 = new String[itls.size()+1];
            int j = 0;
            int x = 1;
            int a = 0;
            for (int y = 1; y <= itlArrayList.size(); y++){
                while(itls.get(x).getGroup().equals(itls.get(x-1).getGroup())
                        && itls.get(x).getTARGET_TABLE().equals(itls.get(x-1).getTARGET_TABLE())){
                    j = j + 1;
                    x = x + 1;
                    if (x == itls.size()){
                        break;
                    }
                }
                StringBuffer sqlContents = new StringBuffer();
                sqlContents.append("--当天数据插入目标表" + LN + "use itl;" + LN + "insert overwrite table "
                        + itls.get(a).getTARGET_TABLE() + " partition(ETL_DT=ETL_DTDATA)" + LN + "select" + LN);
                for (int Z = a; Z <= j; Z++ ){
                    //目标字段
                    String TARGETFIELD = itls.get(Z).getTARGETFIELD();
                    //源字段
                    String STAGE_FIELD = itls.get(Z).getSTAGE_FIELD();
                    //补充字段
                    String LOGIC = itls.get(Z).getLOGIC();
                    //获取源字段映射成目标字段的语句
                    arr1[Z] = getArr(Z, a, TARGETFIELD, STAGE_FIELD, LOGIC);
                    sqlContents.append(arr1[Z]);
                }
                sqlContents.append("from odm." + itls.get(a).getSTAGE_TABLE() + LN);
                contents.put(itls.get(a).getGroup() + "_" + itls.get(a).getTARGET_TABLE(),sqlContents.toString());
                a = x;
                x = x + 1;
                j = j + 1;
                s4 = "";
            }
        }

        if ("RPT".equals(sheetName)) {
            //导入sheet中特定的列的属性为Java对象
            Collection<RPT> dataSrc = ExcelUtil.importSheetCellToClass(clazz, in, sheetName, sqlIndex, logs);
            //将对象转化为list
            ArrayList<RPT> rpts = (ArrayList<RPT>) dataSrc.stream().collect(Collectors.toList());
            //提取对象中的group和target_table，合并后作为唯一标识进行去重
            List<RPT> rptArrayList = rpts.stream().collect(
                    Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(
                                    Comparator.comparing(o ->
                                            o.getGroup() + ";" + o.getTARGET_TABLE()))),
                            ArrayList::new));
            String[] arr1 = new String[rpts.size()+1];
            int j = 0;
            int x = 1;
            int a = 0;
            for (int y = 1; y <= rptArrayList.size(); y++){
                while(rpts.get(x).getGroup().equals(rpts.get(x-1).getGroup())
                        && rpts.get(x).getTARGET_TABLE().equals(rpts.get(x-1).getTARGET_TABLE())){
                    j = j + 1;
                    x = x + 1;
                    if (x == rpts.size()){
                        break;
                    }
                }
                StringBuffer sqlContents = new StringBuffer();
                sqlContents.append("--当天数据插入目标表" + LN + "use rpt;" + LN + "insert overwrite table "
                        + rpts.get(a).getTARGET_TABLE() + " partition(STAT_DT=STAT_DTDATA)" + LN + "select" + LN);
                for (int Z = a; Z <= j; Z++ ){
                    //目标字段
                    String TARGETFIELD = rpts.get(Z).getTARGETFIELD();
                    //源字段
                    String STAGE_FIELD = rpts.get(Z).getSTAGE_FIELD();
                    //补充字段
                    String LOGIC = rpts.get(Z).getLOGIC();
                    //获取源字段映射成目标字段的语句
                    arr1[Z] = getArr(Z, a, TARGETFIELD, STAGE_FIELD, LOGIC);
                    sqlContents.append(arr1[Z]);
                }
                sqlContents.append("from odm." + rpts.get(a).getSTAGE_TABLE() + LN);
                contents.put(rpts.get(a).getGroup()+"_"+rpts.get(a).getTARGET_TABLE(),sqlContents.toString());
                a = x;
                x = x + 1;
                j = j + 1;
                s4 = "";
            }
        }

        contents.entrySet().forEach(
                x -> {
                    StringBuffer stringBuffer = new StringBuffer();
                    //生成文件注释头
                    stringBuffer.append(
                            generate_head(x.getKey(),
                                    StringUtil.trimStr(subject),
                                    StringUtil.trimStr(description),
                                    StringUtil.trimStr(creator)));
                    //加入sql文本
                    stringBuffer.append(x.getValue());
                    //写入文件,文件路径为固定的相对路径
                    String filePath = new File("").getAbsolutePath() + "\\out\\" + sheetName + "Macro" +  "\\" + x.getKey() + ".sql";
                    write2File(filePath, stringBuffer.toString());
                }
        );


    }


    /**
     * @param name        文件名称
     * @param subject     所属主题
     * @param description 功能描述
     * @param creator     创建者
     * @Description 生成文件的头部注释
     * @author Swagger-Ranger
     * @since 2020/2/12 21:38
     */
    public String generate_head( String name, String subject, String description, String creator ) {
        return MessageFormat.format(SqlTitle, name, subject, description, creator, new Date());
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
     * @param Z 当前数据的下标
     * @param a 每张表的第一行的下标
     * @param TARGETFIELD  目标字段
     * @param STAGE_FIELD  源字段
     * @return String   字符串
     * @Description 获取源字段映射成目标字段的语句
     * @author zj
     * @since 2020/04/02 14:46
     */
    private String getArr(int Z, int a, String TARGETFIELD, String STAGE_FIELD, String LOGIC){
        String arr1 = "";
        String LN = "\r\n\r\n";
        if ("Start_Dt".equals(TARGETFIELD)){
            arr1 = ",to_date('$bizdate','yyyymmdd') as Start_Dt" + LN;
        }else if ("End_Dt".equals(TARGETFIELD)){
            arr1 = ",to_date('29990101','yyyymmdd') as End_Dt" + LN;
        }else {
            if (!StringUtil.isNull(LOGIC)){
                if (a == Z){
                    arr1 = LOGIC + " as " + TARGETFIELD + LN;
                }else {
                    arr1 = "," + LOGIC + " as " + TARGETFIELD + LN;
                }
            }else if (!StringUtil.isNull(STAGE_FIELD)){
                if (Z == a){
                    arr1 = STAGE_FIELD + " as " + TARGETFIELD + LN;
                }else {
                    arr1 = "," + STAGE_FIELD + " as " + TARGETFIELD + LN;
                }
            }else {
                arr1 = ",NULL as " + TARGETFIELD + LN;
            }
        }
        return arr1;
    }

    public static void main( String[] args ) {
        NewExcelSqlGenerator newExcelSqlGenerator = new NewExcelSqlGenerator();
        try {
            newExcelSqlGenerator.generate("E:\\MYSH\\ExcelUtil\\src\\test\\resources\\Generate Script TemplateEDW - F1&F2&F5&Append - 修正版.xlsm"
                    , "F5", 12, null, null, null);
//            newExcelSqlGenerator.generate("E:\\MYSH\\ExcelUtil\\src\\test\\resources\\rptMapper - 副本.xls"
//                    , "RPT", 12, null, null, null);
        } catch (FileNotFoundException e) {
            System.err.println("未找到输入文件");
        }

    }
}
