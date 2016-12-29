package com.bufkes.service.recipe.exception;


public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -2691181839608376405L;

	private ServiceError serviceError;

	private ErrorType errorType;

	public ServiceException(ErrorType errorType, ServiceError serviceError) {

		this.errorType = errorType;
		this.serviceError = serviceError;
	}

	public ServiceException(ErrorType errorType, String errorMessage) {

		this(errorType, new ServiceError.Builder().withErrorMessage(errorMessage).build());
	}

	public ErrorType getErrorType() {

		return errorType;
	}

	public ServiceError getServiceError() {

		return serviceError;
	}
}
