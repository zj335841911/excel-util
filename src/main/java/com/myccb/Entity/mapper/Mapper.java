package com.myccb.Entity.mapper;

import com.myccb.util.ExcelUtil.ExcelCell;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author Email:liufei32@outlook.com  github:Swagger-Ranger
 * @Date 2020/2/24 15:34
 * @Description 生成表和字段的映射关系
 */

@Data
@ToString
@Accessors(chain = true)
public class Mapper {

    @ExcelCell(index = 0)
    private String Group;

    @ExcelCell(index = 1)
    private String LOGICAL_ENTITY;

    @ExcelCell(index = 2)
    private String TARGET_TABLE;

    @ExcelCell(index = 3)
    private String TARGET_FIELD;

    @ExcelCell(index = 4)
    private String LOGICAL_ATTRIBUTE;

    @ExcelCell(index = 5)
    private String TARGET_DATA_TYPE;

    @ExcelCell(index = 6)
    private String BLANK_COLUMN;

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

    @ExcelCell(index = 11)
    private String ALGORITHM;


}
