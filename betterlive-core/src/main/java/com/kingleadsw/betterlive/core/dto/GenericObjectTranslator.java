/*
 * Sunyard.com Inc.
 * Copyright (c) $year-2018 All Rights Reserved.
 */

package com.kingleadsw.betterlive.core.dto;

import org.apache.commons.collections.CollectionUtils;

import com.kingleadsw.betterlive.core.convert.BeanConverter;

import java.util.List;


/**
 * 通用象转化
 * <p/>
 * User: sys53
 * Date: 14-12-10 下午12:01
 * version $Id: GenericObjectTranslator.java, v 0.1 Exp $
 */
public class GenericObjectTranslator<S, T> implements ObjectTranslator<S, T> {
    @Override
    public T transfer(T t, S s) {
        if(null == s){
            return null;
        }
        return BeanConverter.convert (t, s);
    }

    @Override
    public List<T> transfer(Class<T> clazz, List<S> ses) {
        if(CollectionUtils.isEmpty(ses)){
            return null;
        }
        return BeanConverter.convert(clazz,ses);
    }

	@Override
	public T transfer(T t, S s, String[] ignores) {
		if(null == s){
            return null;
        }
        return BeanConverter.convert (t, s, ignores);
	}
}
