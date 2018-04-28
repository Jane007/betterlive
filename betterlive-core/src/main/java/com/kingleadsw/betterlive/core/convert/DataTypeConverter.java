/*
 * Sunyard.com Inc.
 * Copyright (c) $year-2018 All Rights Reserved.
 */

package com.kingleadsw.betterlive.core.convert;

/**
 * 数据类型转换接口
 * <p/>
 * User: sys53
 * Date: 14-12-10 上午10:07
 * version $Id: DataTypeConverter.java, v 0.1 Exp $
 */
public interface DataTypeConverter {

    /**
     * Convert the specified input object into an output object of the
     * specified type.
     *
     * @param targetClass Data type to which this value should be converted
     * @param value The input value to be converted
     * @return The converted value
     *
     */
    public Object convert(Class<?> targetClass, Object value);

}
