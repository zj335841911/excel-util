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
            NewMapperGenerator mapperGenerator = new NewMapperGenerator();
            try {
                if (paramLength < 3){
                    System.err.println("请输入3个参数：" + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: 目标表名文件路径" + "\n"
                    );
                }
                if (paramLength == 3 && args[1].contains("ITL")){
                    mapperGenerator.itlGenerateMapper(args[1], args[2]);
                    System.out.println("mapper生成成功");
                } else if (paramLength == 3 && args[1].contains("RPT")){
                    mapperGenerator.rptGenerateMapper(args[1], args[2]);
                    System.out.println("mapper生成成功");
                }else {
                    System.err.println("请输入3个参数：" + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: 目标表名文件路径" + "\n"
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
                        "args[2]: 目标表名文件路径" + "\n"
                );
            }
        }
        else if ("个性化代码".equals(args[0])){
            try {
                if (paramLength < 4){
                    System.err.println("请输入4-7个参数，前4个为必传：" + "\n" +
                        "args[0]: 类型" + "\n" +
                        "args[1]: 源文件路径" + "\n" +
                        "args[2]: sheet名" + "\n" +
                        "args[3]: 结果列下标" + "\n" +
                        "args[4]: 所属主题" + "\n" +
                        "args[5]: 功能描述" + "\n" +
                        "args[6]: 创建者"
                );
                }
            String subject = "";
            String description = "";
            String creator = "";
            if (paramLength == 5) {
                subject = StringUtil.trimStr(args[4]);
            }
            if (paramLength == 6) {
                subject = StringUtil.trimStr(args[4]);
                description = StringUtil.trimStr(args[5]);
            }
            if (paramLength == 7) {
                subject = StringUtil.trimStr(args[4]);
                description = StringUtil.trimStr(args[5]);
                creator = StringUtil.trimStr(args[6]);
            }
            NewExcelSqlGenerator newExcelSqlGenerator = new NewExcelSqlGenerator();
            if (paramLength > 3 && paramLength < 8){
                newExcelSqlGenerator.generate(args[1], args[2], Integer.parseInt(args[3]), subject, description, creator);
                System.out.println("个性化代码生成成功");
            }else {
                System.err.println("请输入4-7个参数，前4个为必传：" + "\n" +
                        "args[0]: 类型" + "\n" +
                        "args[1]: 源文件路径" + "\n" +
                        "args[2]: sheet名" + "\n" +
                        "args[3]: 结果列下标" + "\n" +
                        "args[4]: 所属主题" + "\n" +
                        "args[5]: 功能描述" + "\n" +
                        "args[6]: 创建者"
                );
            }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e1) {
                System.err.println("请输入4-7个参数，前4个为必传：" + "\n" +
                        "args[0]: 类型" + "\n" +
                        "args[1]: 源文件路径" + "\n" +
                        "args[2]: sheet名" + "\n" +
                        "args[3]: 结果列下标" + "\n" +
                        "args[4]: 所属主题" + "\n" +
                        "args[5]: 功能描述" + "\n" +
                        "args[6]: 创建者"
                );
            }
        }
        else if ("数仓模型创表sql语句".equals(args[0])){
            try {
                if (paramLength < 4){
                    System.err.println("请输入4个参数：" + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: 目标表名文件路径" + "\n" +
                            "args[3]: 分桶清单文件路径" + "\n"
                    );
                }
                DWExcelSqlGenerator dwExcelSqlGenerator = new DWExcelSqlGenerator();
                if (paramLength == 4 && args[1].contains("ITL")){
                    dwExcelSqlGenerator.itlSqlgenerate(args[1], args[2], args[3]);
                    System.out.println("数仓模型创表sql语句生成成功");
                }
                else if (paramLength == 4 && args[1].contains("RPT")){
                    dwExcelSqlGenerator.rptSqlgenerate(args[1], args[2], args[3]);
                    System.out.println("数仓模型创表sql语句生成成功");
                }else {
                    System.err.println("请输入4个参数：" + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: 目标表名文件路径" + "\n" +
                            "args[3]: 分桶清单文件路径" + "\n"
                    );
                }
            } catch (FileNotFoundException e) {
                System.err.println("未找到输入文件");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("请输入4个参数：" + "\n" +
                        "args[0]: 类型" + "\n" +
                        "args[1]: 数仓模型源文件路径" + "\n" +
                        "args[2]: 目标表名文件路径" + "\n" +
                        "args[3]: 分桶清单文件路径" + "\n"
                );
            }
        }else if ("sqoop配置文件".equals(args[0])){
            try {
                if (paramLength < 4){
                    System.err.println("请输入5个参数：" + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: itl源文件路径" + "\n" +
                            "args[2]: rpt源文件路径" + "\n" +
                            "args[3]: 目标表名文件路径" + "\n"
                    );
                }
                ConfigGenerator configGenerator = new ConfigGenerator();
                if (paramLength == 4){
                    configGenerator.configGenerator(args[1], args[2], args[3]);
                    System.out.println("sqoop配置文件生成成功");
                }else {
                    System.err.println("请输入4个参数：" + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: itl源文件路径" + "\n" +
                            "args[2]: rpt源文件路径" + "\n" +
                            "args[3]: 目标表名文件路径" + "\n"
                    );
                }
            } catch (FileNotFoundException e) {
                System.err.println("未找到输入文件");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("请输入4个参数：" + "\n" +
                        "args[0]: 类型" + "\n" +
                        "args[1]: itl源文件路径" + "\n" +
                        "args[2]: rpt源文件路径" + "\n" +
                        "args[3]: 目标表名文件路径" + "\n"
                );
            }
        }else {
            System.err.println("参数输入错误：" + "\n" +
                    "args[0]: 类型" + "\n" +
                    "1: mapper  " + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: 目标表名文件路径" + "\n" +
                    "2: 个性化代码  " + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: sheet名" + "\n" +
                            "args[3]: 结果列下标" + "\n" +
                            "args[4]: 所属主题" + "\n" +
                            "args[5]: 功能描述" + "\n" +
                            "args[6]: 创建者" + "\n" +
                    "3: 数仓模型创表sql语句  "  + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: 数仓模型源文件路径" + "\n" +
                            "args[2]: 目标表名文件路径" + "\n" +
                            "args[3]: 分桶清单文件路径" + "\n" +
                    "4: sqoop配置文件"  + "\n" +
                            "args[0]: 类型" + "\n" +
                            "args[1]: itl源文件路径" + "\n" +
                            "args[2]: rpt源文件路径" + "\n" +
                            "args[3]: 目标表名文件路径" + "\n"
            );
        }
    }

    //        ExcelSqlGenerator excelSqlGenerator = new ExcelSqlGenerator();
//        int paramLength = args.length;
//        try {
//            if (paramLength < 3) {
//                System.err.println("请输入3-6个参数，前3个为必传：" + "\n" +
//                        "args[0]: 源文件路径" + "\n" +
//                        "args[1]: sheet名" + "\n" +
//                        "args[2]: 结果列下标" + "\n" +
//                        "args[3]: 所属主题" + "\n" +
//                        "args[4]: 功能描述" + "\n" +
//                        "args[6]: 创建者"
//                );
//            }
//
//            String subject = "";
//            String description = "";
//            String creator = "";
//            if (paramLength == 4) {
//                subject = StringUtil.trimStr(args[3]);
//            }
//            if (paramLength == 5) {
//                subject = StringUtil.trimStr(args[3]);
//                description = StringUtil.trimStr(args[4]);
//            }
//            if (paramLength == 6) {
//                subject = StringUtil.trimStr(args[3]);
//                description = StringUtil.trimStr(args[4]);
//                creator = StringUtil.trimStr(args[5]);
//            }
//
//            excelSqlGenerator.generate(args[0], args[1], Integer.valueOf(args[2]), subject, description, creator);
//        } catch (FileNotFoundException e) {
//            System.err.println("未找到输入文件");
//        } catch (ArrayIndexOutOfBoundsException e1) {
//            System.err.println("请输入3-6个参数，前3个为必传：" + "\n" +
//                    "args[0]: 源文件路径" + "\n" +
//                    "args[1]: sheet名" + "\n" +
//                    "args[2]: 结果列下标" + "\n" +
//                    "args[3]: 所属主题" + "\n" +
//                    "args[4]: 功能描述" + "\n" +
//                    "args[6]: 创建者");
//        }
}

