package com.myccb.util.ExcelUtil;

import lombok.Data;

/**
 * The <code>ExcelLog</code>
 * 
 */

@Data
public class ExcelLog {
    private Integer rowNum;
    private Object object;
    private String log;


    /**
     * @param object
     * @param log
     */
    public ExcelLog(Object object, String log) {
        super();
        this.object = object;
        this.log = log;
    }

    /**
     * @param rowNum
     * @param object
     * @param log
     */
    public ExcelLog(Object object, String log, Integer rowNum) {
        super();
        this.rowNum = rowNum;
        this.object = object;
        this.log = log;
    }

}
