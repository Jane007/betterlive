package com.kingleadsw.betterlive.core.util;

import org.apache.log4j.Logger;

import com.kingleadsw.betterlive.core.enums.EnumAware;

/**
 * 枚举转换工具类
 */
public class EnumConvert {

    private static Logger log = Logger.getLogger(EnumConvert.class);

    /**
     * 根据类的全路径名返回该类的类型
     * @param className
     * @return
     */
    public static EnumAware getEnumByClassName(String className){
        Object[] enumConstants = null;
        try {
            enumConstants = Thread.currentThread().getContextClassLoader().loadClass(className)
                    .getEnumConstants();
        } catch (ClassNotFoundException e) {
            log.info("根据枚举类["+className+"]获取枚举失败:"+e.getMessage());
            return null;
        }
        return ((EnumAware[]) enumConstants)[0];
    }

    /**
     *
     * @param className 需要调用的方法的枚举接口类的路径
     * @param code 转义的编码
     * @return
     */
    public static String getSimpleName(String className,String code){
        if(null == getTargetEnum(className,code )){
            return "";
        }
        return getTargetEnum(className,code ).getSimpleName();
    }

    /**
     *
     * @param className 需要调用的方法的枚举接口类的路径
     * @param code 转义的编码
     * @return
     */
    public static String getName(String className,String code){
       return getTargetEnum(className,code ).getName();
    }

    /**
     * 根据枚举类全路径获取枚举类
     * @param className
     * @param code
     * @return
     */
    public static EnumAware getTargetEnum(String className,String code){
        Object[] enumConstants = null;
        try {
            enumConstants = Thread.currentThread().getContextClassLoader().loadClass(className)
                    .getEnumConstants();
        } catch (ClassNotFoundException e) {
            log.info("根据枚举类["+className+"]获取枚举失败"+e.getMessage());
            return null;
        }
        return ((EnumAware[]) enumConstants)[0].getTargetEnum(code);
    }
}
