package com.myccb;

import com.myccb.SqlGenerate.ExcelSqlGenerator;
import java.io.FileNotFoundException;

/**
 * @Author Email:liufei32@outlook.com  github:Swagger-Ranger
 * @Date 2020/2/13 16:37
 * @Description 启动类
 */

public class GenerateApplication {

    public static void main( String[] args ) {
        ExcelSqlGenerator excelSqlGenerator = new ExcelSqlGenerator();
        try {
            excelSqlGenerator.generate(args[0], args[1], Integer.valueOf(args[2]), args[3],args[3],args[3]);
        } catch (FileNotFoundException e) {
            System.err.println("未找到输入文件");
        } catch (ArrayIndexOutOfBoundsException e1) {
            System.err.println("请输入必要的执行参数：" + "\n" +
                    "args[0]: 源文件路径" + "\n" +
                    "args[1]: 所属主题" + "\n" +
                    "args[2]: 功能描述" + "\n" +
                    "args[3]: 创建者");
        }
    }

}
