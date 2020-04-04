package com.shiliang.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtils {

    /**
     * 分页使用，判断是否是大于0的数字
     */
    private static Pattern pattern = Pattern.compile("^[1-9][0-9]*$");


    /**
     * 获得页码
     *  null aaaa
     * @return 返回>=1的数字
     * 如果给定的字符串不合法，返回1
     */
    public static int getPageCode(String str) {
        if (isNumber(str)) {
            return Integer.parseInt(str);
        }
        return 1;
    }

    /**
     * 是不是数字
     *
     * @param num
     */
    public static boolean isNumber(String num) {
        if (!isValid(num)) {
            return false;
        }
        Matcher matcher = pattern.matcher(num);
        return matcher.matches();
    }

    /**
     * 判断是否是有效的字符串
     * @param str
     * @return true 有效
     */
    public static boolean isValid(String str) {
        return str != null && !str.trim().equals("");
    }

}
