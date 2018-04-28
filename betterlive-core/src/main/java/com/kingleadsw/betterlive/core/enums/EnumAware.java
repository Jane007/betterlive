/*
 * Sunyard.com Inc.
 * Copyright (c) $year-2018 All Rights Reserved.
 */

package com.kingleadsw.betterlive.core.enums;

/**
 * 枚举接口
 * <p>
 *     所有业务的枚举都要实现本接口
 * </p>
 * User: sys53
 * Date: 15-8-20 上午8:43
 * version $Id: EnumAware.java, v 0.1 Exp $
 */
public interface EnumAware {
	/**
	 * 根据编码获取枚举值
	 *
	 * @param code 编码
	 * @return 目标枚举值
	 */
	EnumAware getTargetEnum(String code);

	/**
	 * Get all enums.Apply to custom, such as enumerating the need to sort
	 *
	 * @return the enum aware [ ]
	 */
	public EnumAware[] getAllEnums();

	/**
	 * Get the enum code.
	 *
	 * @return the code
	 */
	String getCode();

	/**
	 * Get the enum name
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Get the simpleName
	 *
	 * @return the simpleName
	 */
	String getSimpleName();
}
