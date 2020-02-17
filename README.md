
1. 源文件EXCEL中Append sheet页名字必须为Append，因为代码根据名字查找
2. Append sheet页中的数据列必须按照如下排列并且不能有空白列，因为代码中作数据模型时是按列标映射的：
    ```java
    @Data
    @ToString
    public class AppendModel {

        @ExcelCell(index = 0)
        private String Group;
        @ExcelCell(index = 1)
        private String LOGICAL_ENTITY;
        @ExcelCell(index = 2)
        private String TARGET_TABLE;
        @ExcelCell(index = 3)
        private String TARGETFIELD;
        @ExcelCell(index = 4)
        private String LOGICAL_ATTRIBUTE;
        @ExcelCell(index = 5)
        private String TARGET_DATA_TYPE;
        @ExcelCell(index = 6)
        private String STAGE_TABLE;
        @ExcelCell(index = 7)
        private String STAGE_FIELD;
        @ExcelCell(index = 8)
        private String 源字段描述;
        @ExcelCell(index = 9)
        private String SOURCE_DATA_TYPE;
        @ExcelCell(index = 10)
        private String LOGIC;
        @ExcelCell(index = 11)
        private String Generate_Text;

    }
    ```    
    
3. 执行命令 java -jar ExcelSqlGenerator-1.0.0-SNAPSHOT.jar {源文件路径} {文件注释中所属主题} {文件注释中功能描述} {文件注释中创建者}
比如：
 ```
 java -jar ExcelSqlGenerator-1.0.0-SNAPSHOT.jar "C:\Users\Administrator\Desktop\历史数据平台\source\Generate Script TemplateEDW - F1&F2&F5&Append.xlsm" test test liufei
 ```
 **注意：如果文件名中有空格，就将对应参数引起来，如上示例
 
4. 生成的SQL文件在与执行文件同级的out目录下
 **注意：out是和执行文件固定的相对路径，没有out文件夹就会报找不到目标路径的错。所以不要改目录结构，拷执行jar包连同文件夹一起拷就OK了**


