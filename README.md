Excel工具：
        1、mapper的自动生成
        
        2、个性化代码生成
        
        3、数仓模型创表sql语句自动生成
        
        4、sqoop配置文件自动生成
    
**注意：

1. 源文件EXCEL中 sheet页名字必须与Entity实体名相同，因为代码根据名字查找
    
2. 执行命令 java -jar ExcelSqlGenerator-1.0.0-SNAPSHOT.jar {类型} {参数1} {参数2} {参数3} ......
    比如：
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

 ```
    java -jar ExcelSqlGenerator-1.0.0-SNAPSHOT.jar "mapper" "E:\MYSH\needExcel\（数仓模型）ITL层.xlsx" "E:\MYSH\needExcel\ITL_RPT_日数据量统计_20200119(1).xlsx"
    java -jar ExcelSqlGenerator-1.0.0-SNAPSHOT.jar "个性化代码" "E:\MYSH\needExcel\Generate Script TemplateEDW - F1&F2&F5&Append - 修正版.xlsm" "Map" 12
    java -jar ExcelSqlGenerator-1.0.0-SNAPSHOT.jar "数仓模型创表sql语句" "E:\MYSH\needExcel\（数仓模型）ITL层.xlsx" "E:\MYSH\needExcel\ITL_RPT_日数据量统计_20200119(1).xlsx" "E:\MYSH\needExcel\ITL_分桶清单.xlsx"
    java -jar ExcelSqlGenerator-1.0.0-SNAPSHOT.jar "sqoop配置文件" "E:\MYSH\needExcel\（数仓模型）ITL层.xlsx" "E:\MYSH\needExcel\（数仓模型）RPT层.xlsx" "E:\MYSH\needExcel\ITL_RPT_日数据量统计_20200119(1).xlsx"
 ```
 **注意：如果文件名中有空格，就将对应参数引起来，如上示例
 
4. 生成的SQL文件在与执行文件同级的out目录下
 **注意：out是和执行文件固定的相对路径，没有out文件夹就会报找不到目标路径的错。所以不要改目录结构，拷执行jar包连同文件夹一起拷就OK了**


