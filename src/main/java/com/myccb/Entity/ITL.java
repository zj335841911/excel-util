package com.myccb.Entity;

import com.myccb.util.ExcelUtil.ExcelCell;
import lombok.Data;
import lombok.ToString;

/**
 * @Author Email:liufei32@outlook.com  github:Swagger-Ranger
 * @Date 2020/2/12 17:05
 * @Description
 */

@Data
@ToString
public class ITL {

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
    private String BLANK_LINE;
    @ExcelCell(index = 7)
    private String STAGE_TABLE;
    @ExcelCell(index = 8)
    private String STAGE_FIELD;
    @ExcelCell(index = 9)
    private String 源字段描述;
    @ExcelCell(index = 10)
    private String SOURCE_DATA_TYPE;
    @ExcelCell(index = 11)
    private String LOGIC;
    @ExcelCell(index = 12)
    private String Generate_Text;

}
