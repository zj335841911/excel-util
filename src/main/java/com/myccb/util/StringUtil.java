package com.myccb.util;

/**
 * @Author Email:liufei32@outlook.com  github:Swagger-Ranger
 * @Date 2020/2/13 12:07
 * @Description 字符串工具类
 */

public class StringUtil {

    /**
     * @param str 字符串
     * @return boolean
     * @Description 判断字符串是否非空
     * @author Swagger-Ranger
     * @since 2020/2/13 12:25
     */
    public static boolean isNull( String str ) {
        if (null == str) {
            return true;
        }

        if ("" == str.trim()) {
            return true;
        }
        return false;
    }

    /**
     * @param str
     * @return java.lang.String
     * @Description 将字符串作trim
     * @author Swagger-Ranger
     * @since 2020/2/13 13:12
     */
    public static String trimStr( String str ) {

        if (null == str) {
            return "";
        } else {
            return str.trim();
        }
    }


}
