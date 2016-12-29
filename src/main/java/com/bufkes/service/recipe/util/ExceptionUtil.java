package com.bufkes.service.recipe.util;

import com.bufkes.service.recipe.exception.ErrorType;
import com.bufkes.service.recipe.exception.ServiceException;

public class ExceptionUtil {

	public static void throwServiceException(final ErrorType errorType, final String errorMessage) throws ServiceException {

        throw new ServiceException(errorType, errorMessage);
    }
}
