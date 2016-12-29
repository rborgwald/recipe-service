package com.bufkes.service.recipe.exception;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

public class ServiceExceptionHandlerTest {

private ServiceExceptionHandler serviceExceptionHandler;
	
	public ServiceExceptionHandlerTest() {
		MockitoAnnotations.initMocks(this);
	}

	@Before
	public void setup() {

		serviceExceptionHandler = new ServiceExceptionHandler();
	}
	
	@Test
	public void testServiceErrorHandler(){
		
		ServiceException serviceException = new ServiceException(ErrorType.AUTHENTICATION, "message");
		
		Assertions.assertThat(serviceExceptionHandler.serviceErrorHandler(serviceException))
		.extracting("body.errorMessage", "statusCode", "statusCode.name", "statusCode.reasonPhrase")
		.containsExactly("message",HttpStatus.BAD_REQUEST, "BAD_REQUEST", "Bad Request");
	
	}

	@Test
	public void testGenericErrorHandler(){
		
		ServiceException serviceException = new ServiceException(ErrorType.AUTHENTICATION, "message");
		
		Assertions.assertThat(serviceExceptionHandler.genericErrorHandler(serviceException))
		.extracting("statusCode", "statusCode.name", "statusCode.reasonPhrase", "statusCode.value")
		.containsExactly(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "Internal Server Error", 500);
		
		
		
	}
}
