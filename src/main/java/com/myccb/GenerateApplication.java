package com.myccb;

import com.myccb.Generator.ConfigGenerator;
import com.myccb.Generator.DWExcelSqlGenerator;
import com.myccb.Generator.NewExcelSqlGenerator;
import com.myccb.Generator.NewMapperGenerator;
import com.myccb.util.StringUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author Email:liufei32@outlook.com  github:Swagger-Ranger
 * @Date 2020/2/13 16:37
 * @Description 启动类
 */

public class GenerateApplication {

    public static void main( String[] args ) {
        int paramLength = args.length;
        if ("mapper".equals(args[0])){
            try {
                if (paramLength < 3){
                    System.err.println("请输入3个参数：" + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: sheetName" + "\n"
                    );
                }
                if (paramLength == 3 ){
                    NewMapperGenerator mapperGenerator = new NewMapperGenerator();
                    mapperGenerator.generateMapper(args[1], args[2]);
                    System.out.println("mapper生成成功");
                } else {
                    System.err.println("请输入3个参数：" + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: sheetName" + "\n"
                    );
                }
            } catch (IOException e) {
                System.err.println("未找到文件");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("请输入3个参数：" + "\n" +
                        "args[0]: 类型" + "\n" +
                        "args[1]: 数仓模型源文件路径" + "\n" +
                        "args[2]: sheetName" + "\n"
                );
            }
        }
        else if ("个性化代码".equals(args[0])){
            try {
                if (paramLength < 3){
                    System.err.println("请输入3-6个参数，前3个为必传：" + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: mapper源文件路径" + "\n" +
                            "args[2]: sheet名" + "\n" +
                            "args[3]: 所属主题" + "\n" +
                            "args[4]: 功能描述" + "\n" +
                            "args[5]: 创建者" + "\n"
                );
                }
            String subject = "";
            String description = "";
            String creator = "";
            if (paramLength == 4) {
                subject = StringUtil.trimStr(args[3]);
            }
            if (paramLength == 5) {
                subject = StringUtil.trimStr(args[3]);
                description = StringUtil.trimStr(args[4]);
            }
            if (paramLength == 6) {
                subject = StringUtil.trimStr(args[3]);
                description = StringUtil.trimStr(args[4]);
                creator = StringUtil.trimStr(args[5]);
            }
            NewExcelSqlGenerator newExcelSqlGenerator = new NewExcelSqlGenerator();
            if (paramLength > 2 && paramLength < 7){
                newExcelSqlGenerator.generate(args[1], args[2], subject, description, creator);
                System.out.println(args[2] + "个性化代码生成成功");
            }else {
                System.err.println("请输入3-6个参数，前3个为必传：" + "\n" +
                        "args[0]: 类型" + "\n" +
                        "args[1]: mapper源文件路径" + "\n" +
                        "args[2]: sheet名" + "\n" +
                        "args[3]: 所属主题" + "\n" +
                        "args[4]: 功能描述" + "\n" +
                        "args[5]: 创建者" + "\n"
                );
            }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e1) {
                System.err.println("请输入3-6个参数，前3个为必传：" + "\n" +
                        "args[0]: 类型" + "\n" +
                        "args[1]: mapper源文件路径" + "\n" +
                        "args[2]: sheet名" + "\n" +
                        "args[3]: 所属主题" + "\n" +
                        "args[4]: 功能描述" + "\n" +
                        "args[5]: 创建者" + "\n"
                );
            }
        }
        else if ("数仓模型创表sql语句".equals(args[0])){
            try {
                if (paramLength < 3){
                    System.err.println("请输入3个参数：" + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: sheet名" + "\n"
                    );
                }
                if (paramLength == 3 ){
                    DWExcelSqlGenerator dwExcelSqlGenerator = new DWExcelSqlGenerator();
                    dwExcelSqlGenerator.sqlgenerate(args[1], args[2]);
                    System.out.println("数仓模型创表sql语句生成成功");
                }else {
                    System.err.println("请输入3个参数：" + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: sheet名" + "\n"
                    );
                }
            } catch (FileNotFoundException e) {
                System.err.println("未找到输入文件");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("请输入3个参数：" + "\n" +
                        "args[0]: 类型" + "\n" +
                        "args[1]: 数仓模型源文件路径" + "\n" +
                        "args[2]: sheet名" + "\n"
                );
            }
        }else if ("sqoop配置文件".equals(args[0])){
            try {
                if (paramLength < 3){
                    System.err.println("请输入3个参数：" + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: sheetName" + "\n"
                    );
                }
                if (paramLength == 3){
                    ConfigGenerator configGenerator = new ConfigGenerator();
                    configGenerator.configGenerator(args[1], args[2]);
                    System.out.println("sqoop配置文件生成成功");
                }else {
                    System.err.println("请输入3个参数：" + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: sheetName" + "\n"
                    );
                }
            } catch (FileNotFoundException e) {
                System.err.println("未找到输入文件");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("请输入3个参数：" + "\n" +
                        "args[0]: 类型" + "\n" +
                        "args[1]: 数仓模型源文件路径" + "\n" +
                        "args[2]: sheetName" + "\n"
                );
            }
        }else if ("一键生成".equals(args[0])){
            try {
                if (paramLength < 3){
                    System.err.println("请输入3个参数：" + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: sheetName" + "\n"
                    );
                }
                if (paramLength == 3){
                    NewMapperGenerator mapperGenerator = new NewMapperGenerator();
                    mapperGenerator.generateMapper(args[1], args[2]);
                    System.out.println(args[2] + "mapper生成成功");
                    DWExcelSqlGenerator dwExcelSqlGenerator = new DWExcelSqlGenerator();
                    dwExcelSqlGenerator.sqlgenerate(args[1], args[2]);
                    System.out.println(args[2] + "数仓模型创表sql语句生成成功");
                    ConfigGenerator configGenerator = new ConfigGenerator();
                    configGenerator.configGenerator(args[1], args[2]);
                    System.out.println(args[2] + "sqoop配置文件生成成功");
                }else {
                    System.err.println("请输入3个参数：" + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: sheetName" + "\n"
                    );
                }
            } catch (FileNotFoundException e) {
                System.err.println("未找到输入文件");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("请输入3个参数：" + "\n" +
                        "args[0]: 类型" + "\n" +
                        "args[1]: 数仓模型源文件路径" + "\n" +
                        "args[2]: sheetName" + "\n"
                );
            }
        }else {
            System.err.println("参数输入错误：" + "\n" +
                    "args[0]: 类型" + "\n" +
                    "1: mapper  " + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: sheetName" + "\n" +
                    "2: 个性化代码  " + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 源文件路径" + "\n" +
                            "args[2]: sheet名" + "\n" +
                            "args[3]: 所属主题" + "\n" +
                            "args[4]: 功能描述" + "\n" +
                            "args[5]: 创建者" + "\n" +
                    "3: 数仓模型创表sql语句  "  + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: sheet名" + "\n" +
                    "4: sqoop配置文件"  + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: sheetName" + "\n" +
                    "5: 一键生成"  + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: sheetName" + "\n"
            );
        }
    }
}

