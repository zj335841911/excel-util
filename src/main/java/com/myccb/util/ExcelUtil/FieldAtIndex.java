package com.myccb.util.ExcelUtil;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * The <code>FieldAtIndex</code>
 * EXCEL中列index和属性的对应
 */

@Data
public class FieldAtIndex {
    private Field field;
    private int index;

    /**
     * @param field
     */
    public FieldAtIndex( Field field) {
        super();
        this.field = field;
    }

    /**
     * @param field
     * @param index
     */
    public FieldAtIndex( Field field, int index) {
        super();
        this.field = field;
        this.index = index;
    }

}
