package com.myccb.Entity.mapper;

import com.myccb.util.ExcelUtil.ExcelCell;
import lombok.Data;

/**
 * @Company silinx 本质思考，快速行动，价值创造
 * @Author Email:liufei32@outlook.com  github:Swagger-Ranger
 * @Date 2020/2/24 16:11
 * @Description
 */

@Data
public class ITL_RPT {

    @ExcelCell(index = 0)
    private String serialNo;

    @ExcelCell(index = 1)
    private String tableName;

    @ExcelCell(index = 2)
    private String records;
}
