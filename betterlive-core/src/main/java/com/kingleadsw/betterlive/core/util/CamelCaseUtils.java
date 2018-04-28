/*
 * Sunyard.com Inc.
 * Copyright (c) $year-2018 All Rights Reserved.
 */

package com.kingleadsw.betterlive.core.util;

import org.apache.commons.lang.StringUtils;

import java.util.StringTokenizer;

/**
 * 骆驼命名转换辅助类
 * <p/>
 * User: sys53
 * Date: 14-12-10 上午10:07
 * version $Id: CamelCaseUtils.java, v 0.1 Exp $
 */
public class CamelCaseUtils {

    /**
     * 骆驼命名拆分回下划线
     *
     * @param name the name
     * @return the string
     */
    public static String camelCase2Underline(String name) {

        if (StringUtils.isBlank (name)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < name.length(); i++) {

            char c = name.charAt(i);

            if (i > 0 && Character.isUpperCase(c)) {
                sb.append("_");
            }

            sb.append(c);
        }

        return sb.toString().toUpperCase();
    }
    public static String removeAndHump(String data) {
        return removeAndHump(data, "_");
    }

    public static String removeAndHump(String data, String replaceThis) {
        StringBuffer out = new StringBuffer();
        StringTokenizer st = new StringTokenizer(data, replaceThis);

        while(st.hasMoreTokens()) {
            String element = (String)st.nextElement();
            out.append(capitalizeFirstLetter(element));
        }

        return out.toString();
    }

    public static String capitalizeFirstLetter(String data) {
        String firstLetter = data.substring(0, 1).toUpperCase();
        String restLetters = data.substring(1);
        return firstLetter + restLetters;
    }

}
