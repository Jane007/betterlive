/*
 * Sunyard.com Inc.
 * Copyright (c) $year-2018 All Rights Reserved.
 */

package com.kingleadsw.betterlive.core.dto;

import java.util.List;

/**
 * DTO 通用接口
 * <p/>
 * User: sys53
 * Date: 14-12-10 上午10:42
 * version $Id: ObjectTranslator.java, v 0.1 Exp $
 */
public interface ObjectTranslator<S,T> {

    /**
     * 源对象 转化为 目标对象
     * @param t 目标对象
     * @param s 源对象
     * @return
     */
     T transfer(T t, S s);
     
     /**
      * 源对象 转化为 目标对象
      * @param t 目标对象
      * @param s 源对象
      * @return
      */
      T transfer(T t, S s,String[] ignores);

    /**
     * 源对象集转 转化为 目标集合
     * @param clazz
     * @param ses
     * @return
     */
    List<T> transfer(Class<T> clazz, List<S> ses);


}
