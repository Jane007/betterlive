/*
 * Sunyard.com Inc.
 * Copyright (c) 2015-2018 All Rights Reserved.
 */

package com.kingleadsw.betterlive.core.exception;

/**
 * 基于{@link Throwable}系统的转换类异常类
 * <p/>
 * User: sys53
 * Date: 15-8-17 下午2:43
 * version $Id: P2pException.java, v 0.1 Exp $
 */
public class ConvertException extends RuntimeException {

    private static final long serialVersionUID = -1554198796275672430L;

    public ConvertException() {
        super();
    }

    public ConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(Throwable cause) {
        super(cause);
    }
}
