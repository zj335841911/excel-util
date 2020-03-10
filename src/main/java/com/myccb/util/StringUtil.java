package com.myccb.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @since 2020/2/14 13:12
     */
    public static String trimStr( String str ) {

        if (null == str) {
            return "";
        } else {
            return str.trim();
        }
    }

    /**
     * @Description 判断字符串是否能转换为数字
     * @param str 字符串
     * @return boolean
     * @author Swagger-Ranger
     * @since 2020/2/26 18:24
     */
    public static boolean isNumberString( String str ) {

        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


}
