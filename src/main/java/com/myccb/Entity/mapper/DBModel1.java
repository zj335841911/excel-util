package com.myccb.Entity.mapper;

import com.myccb.util.ExcelUtil.ExcelCell;
import lombok.Data;
import lombok.ToString;

/**
 * @Company silinx 本质思考，快速行动，价值创造
 * @Author Email:liufei32@outlook.com  github:Swagger-Ranger
 * @Date 2020/2/24 17:50
 * @Description 数仓模型
 */

@Data
@ToString
public class DBModel1 {

    @ExcelCell(index = 0)
    private String 序号;

    @ExcelCell(index = 1)
    private String 表名;

    @ExcelCell(index = 2)
    private String 表名备注;

    @ExcelCell(index = 3)
    private String 列名;

    @ExcelCell(index = 4)
    private String 数据类型;

    @ExcelCell(index = 5)
    private String 字段长度;

    @ExcelCell(index = 6)
    private String 整数位;

    @ExcelCell(index = 7)
    private String 小数位;

    @ExcelCell(index = 8)
    private Double COLUMN_ID;

    @ExcelCell(index = 9)
    private String 列名备注;

    @ExcelCell(index = 10)
    private Double 分桶个数;

    @ExcelCell(index = 11)
    private String 是否分布键;

    @ExcelCell(index = 12)
    private String 是否有效;

    @ExcelCell(index = 13)
    private String 算法;

}
