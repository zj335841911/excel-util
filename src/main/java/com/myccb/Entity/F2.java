package com.myccb.Entity;

import com.myccb.util.ExcelUtil.ExcelCell;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author zj
 * @Date 2020/04/02 17:11
 * @Description
 */
@Data
@ToString
@Accessors(chain = true)
public class F2 {

    @ExcelCell(index = 0)
    private String Table_Name;
    @ExcelCell(index = 1)
    private String Primary_Key;
    @ExcelCell(index = 2)
    private String Need_Update;
    @ExcelCell(index = 3)
    private String Script;
    @ExcelCell(index = 4)
    private String Temp_Table;
    @ExcelCell(index = 5)
    private String Owner;

}
