package com.bufkes.service.recipe.util;

import java.text.MessageFormat;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.bufkes.service.recipe.exception.ErrorType;

public class Assert {

	/**
	 * Uses internally {@link BooleanUtils#isTrue(Boolean)} to handle null
	 * Boolean and treat them as false.
	 *
	 * @param booleanExpression
	 * @param errorMessage
	 * @param params
	 */
	public static void isTrue(final Boolean booleanExpression, final String errorMessage, final Object... params) {

		isTrue(booleanExpression, null, errorMessage, "Boolean expression should be true.", params);
	}

	/**
	 * Uses internally {@link BooleanUtils#isTrue(Boolean)} to handle null
	 * Boolean and treat them as false.
	 *
	 * @param booleanExpression
	 * @param errorType
	 * @param errorMessage
	 * @param params
	 */
	public static void isTrue(final Boolean booleanExpression, final ErrorType errorType, final String errorMessage,
			final Object... params) {

		isTrue(booleanExpression, errorType, errorMessage, "Boolean expression should be true.", params);
	}

	/**
	 * This is the core which all other Assert methods are going to use.
	 *
	 * @param booleanExpression
	 * @param errorType
	 * @param errorMessage
	 * @param defaultErrorMessage
	 * @param params
	 */
	private static void isTrue(final Boolean booleanExpression, final ErrorType errorType, String errorMessage,
			final String defaultErrorMessage, final Object... params) {

		boolean result = BooleanUtils.isTrue(booleanExpression);
		if (!result) {
			if (StringUtils.isBlank(errorMessage)) {
				errorMessage = defaultErrorMessage;
			} else {
				errorMessage = MessageFormat.format(errorMessage, params);
			}
			ExceptionUtil.throwServiceException(errorType, errorMessage);
		}
	}

}
