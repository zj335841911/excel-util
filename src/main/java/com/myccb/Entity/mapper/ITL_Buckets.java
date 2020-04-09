package com.myccb.Entity.mapper;

import com.myccb.util.ExcelUtil.ExcelCell;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *
 * @author zj
 * @version 1.0
 * @Description itl分桶清单
 * @date 2020/3/26 09:23
 */

@Data
@ToString
@Accessors(chain = true)
public class ITL_Buckets {

    @ExcelCell(index = 2)
    private String 表名;

    @ExcelCell(index = 13)
    private String 是否分桶最终;

    @ExcelCell(index = 16)
    private Double 分桶个数取质数;

    @ExcelCell(index = 17)
    private String 分布键;



}
