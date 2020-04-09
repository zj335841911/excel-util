package com.myccb.Entity.mapper;

import com.myccb.util.ExcelUtil.ExcelCell;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *
 * @author zj
 * @version 1.0
 * @Description rpt分桶清单
 * @date 2020/3/30 10:31
 */

@Data
@ToString
@Accessors(chain = true)
public class RPT_Buckets {

    @ExcelCell(index = 0)
    private String 表名;

    @ExcelCell(index = 1)
    private String 是否分桶最终;

    @ExcelCell(index = 2)
    private Double 分桶个数取质数;

    @ExcelCell(index = 3)
    private String 分布键;



}
