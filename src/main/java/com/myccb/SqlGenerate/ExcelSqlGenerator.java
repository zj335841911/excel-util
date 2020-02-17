package com.myccb.SqlGenerate;

import com.myccb.Entity.Append;
import com.myccb.Entity.F5;
import com.myccb.util.ExcelUtil.ExcelLogs;
import com.myccb.util.ExcelUtil.ExcelUtil;
import com.myccb.util.StringUtil;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * @Author Email:liufei32@outlook.com  github:Swagger-Ranger
 * @Date 2020/2/12 18:38
 * @Description 读取Excel并写入到SQL文本中
 */

public class ExcelSqlGenerator {

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
     * @author Swagger-Ranger
     * @since 2020/2/12 22:20
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

        Map<String, String> contents = null;

        //针对不同的sheet作不同的解析
        if ("Append".equals(sheetName)) {
            Collection<Append> dataSrc = ExcelUtil.importStringSheetToClass(clazz, in, sheetName, sqlIndex, logs);
            contents = dataSrc.stream()
                    .collect(toMap(
                            Append::getTARGET_TABLE,//TARGET_TABLE作为键
                            appendModel -> StringUtil.trimStr(appendModel.getGenerate_Text()),//Generate_Text作为值
                            ( x, y ) -> StringUtil.trimStr(x) + "\n" + StringUtil.trimStr(y)));//Generate_Text的值字符串作拼接
        }
        if ("F5".equals(sheetName)) {
            Collection<F5> dataSrc = ExcelUtil.importStringSheetToClass(clazz, in, sheetName, sqlIndex, logs);
            contents = dataSrc.stream()
                    .collect(toMap(
                            F5::getTable_Name,//TARGET_Name作为键
                            F5 -> StringUtil.trimStr(F5.getScript()),//Script作为值
                            ( x, y ) -> StringUtil.trimStr(x) + "\n" + StringUtil.trimStr(y)));//Generate_Text的值字符串作拼接
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
                    String filePath = new File("").getAbsolutePath() + "\\out\\" + sheetName + "\\" + x.getKey() + ".sql";
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

    public static void main( String[] args ) {
        ExcelSqlGenerator excelSqlGenerator = new ExcelSqlGenerator();
        try {
//            excelSqlGenerator.generate("./src/test/resources/Generate Script TemplateEDW - F1&F2&F5&Append - 副本.xlsm"
//                    , "F5", 3, null, null, null);
            excelSqlGenerator.generate("./src/test/resources/Generate Script TemplateEDW - F1&F2&F5&Append - 副本.xlsm"
                    , "Append", 11, null, null, null);
        } catch (FileNotFoundException e) {
            System.err.println("未找到输入文件");
        }

//        excelSqlGenerator.write2File("./out.sql","aaaaaaaaaaaaaaaa");
    }
}
