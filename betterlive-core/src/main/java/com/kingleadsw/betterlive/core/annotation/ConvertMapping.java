/*
 * Sunyard.com Inc.
 * Copyright (c) $year-2018 All Rights Reserved.
 */

package com.kingleadsw.betterlive.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 属性转换注解，此注解用作标识属性转换时的属性映射
 *
 * @author sys53
 * @version $Id: ConvertMapping.java, v 0.1 2014-12-10 上午10:35:43 sys53 Exp $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface ConvertMapping {

    /** 源字段名称 */
    String origField() default "";

    /** map key 名称 */
    String mapKey() default "";

}
