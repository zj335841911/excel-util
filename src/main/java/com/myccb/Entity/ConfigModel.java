package com.myccb.Entity;

import com.myccb.util.ExcelUtil.ExcelCell;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * TODO
 *
 * @author zj
 * @version 1.0
 * @date 2020/03/31 11:25
 */
@Data
@ToString
@Accessors(chain = true)
public class ConfigModel {

    @ExcelCell(index = 0)
    private String config_table_name;

    @ExcelCell(index = 1)
    private String table_name;

    @ExcelCell(index = 2)
    private String src_sys_short_name;

    @ExcelCell(index = 3)
    private String table_comments;

    @ExcelCell(index = 4)
    private Integer column_id;

    @ExcelCell(index = 5)
    private String column_name;

    @ExcelCell(index = 6)
    private String column_comments;

    @ExcelCell(index = 7)
    private String column_data_type;

    @ExcelCell(index = 8)
    private String data_precision;

    @ExcelCell(index = 9)
    private String data_scale;

    @ExcelCell(index = 10)
    private String data_length;

}
