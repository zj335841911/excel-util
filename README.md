Excel工具：
    1、mapper的自动生成
    2、个性化代码生成
    3、数仓模型创表sql语句自动生成
    4、sqoop配置文件自动生成
    
**注意：

1. 源文件EXCEL中 sheet页名字必须与Entity实体名相同，因为代码根据名字查找
    
2. 执行命令 java -jar ExcelSqlGenerator-1.0.0-SNAPSHOT.jar {源文件路径} {文件注释中所属主题} {文件注释中功能描述} {文件注释中创建者}
比如：
 ```
 java -jar ExcelSqlGenerator-1.0.0-SNAPSHOT.jar "C:\Users\Administrator\Desktop\历史数据平台\source\Generate Script TemplateEDW - F1&F2&F5&Append.xlsm" test test liufei
 ```
 **注意：如果文件名中有空格，就将对应参数引起来，如上示例
 
4. 生成的SQL文件在与执行文件同级的out目录下
 **注意：out是和执行文件固定的相对路径，没有out文件夹就会报找不到目标路径的错。所以不要改目录结构，拷执行jar包连同文件夹一起拷就OK了**


