package com.kingleadsw.betterlive.core.util;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2016-11-18.
 */
public class SignUtil {
    private static final Logger logger = Logger.getLogger(SignUtil.class);

    public static String getSign(Map<String, String> map, String sign) {

        StringBuilder sb = new StringBuilder();

        Map<String, String> resultMap = sortMapByKey(map);

        sb.setLength(0);
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            sb.append(entry.getValue());
        }
        String result = sb.toString();
        result += sign;
        logger.info("Sign Before:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        logger.info("Sign After:" + result);

        return result;
    }

    /**根据key排序
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }
}
