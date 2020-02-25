package com.myccb;

import com.myccb.Generator.ExcelSqlGenerator;
import com.myccb.util.StringUtil;

import java.io.FileNotFoundException;

/**
 * @Author Email:liufei32@outlook.com  github:Swagger-Ranger
 * @Date 2020/2/13 16:37
 * @Description 启动类
 */

public class GenerateApplication {

    public static void main( String[] args ) {
        ExcelSqlGenerator excelSqlGenerator = new ExcelSqlGenerator();
        int paramLength = args.length;
        try {
            if (paramLength < 3) {
                System.err.println("请输入3-6个参数，前3个为必传：" + "\n" +
                        "args[0]: 源文件路径" + "\n" +
                        "args[1]: sheet名" + "\n" +
                        "args[2]: 结果列下标" + "\n" +
                        "args[3]: 所属主题" + "\n" +
                        "args[4]: 功能描述" + "\n" +
                        "args[6]: 创建者"
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

            excelSqlGenerator.generate(args[0], args[1], Integer.valueOf(args[2]), subject, description, creator);
        } catch (FileNotFoundException e) {
            System.err.println("未找到输入文件");
        } catch (ArrayIndexOutOfBoundsException e1) {
            System.err.println("请输入3-6个参数，前3个为必传：" + "\n" +
                    "args[0]: 源文件路径" + "\n" +
                    "args[1]: sheet名" + "\n" +
                    "args[2]: 结果列下标" + "\n" +
                    "args[3]: 所属主题" + "\n" +
                    "args[4]: 功能描述" + "\n" +
                    "args[6]: 创建者");
        }
    }

}
