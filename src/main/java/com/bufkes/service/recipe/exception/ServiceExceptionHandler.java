package com.bufkes.service.recipe.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ServiceExceptionHandler {

    /*
    private static HttpStatus getResponseStatus(ErrorType errorType) {

        HttpStatus status;
        switch (errorType) {
            case SYSTEM:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            case CONCURRENT_UPDATE:
                status = HttpStatus.CONFLICT;
                break;
            case NO_DATA_FOUND:
                status = HttpStatus.NOT_FOUND;
                break;
            case AUTHENTICATION:
                status = HttpStatus.FORBIDDEN;
                break;
            case AUTHORIZATION:
                status = HttpStatus.UNAUTHORIZED;
                break;
            case UNPROCESSABLE_ENTITY:
                status = HttpStatus.UNPROCESSABLE_ENTITY;
                break;
            case FORBIDDEN:
                status = HttpStatus.FORBIDDEN;
                break;
            default:
                status = HttpStatus.BAD_REQUEST;
                break;
        }
        return status;
    }
    */

    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public ResponseEntity<ServiceError> serviceErrorHandler(ServiceException ex) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<ServiceError> resp = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .headers(headers)
                .body(ex.getServiceError());


        return resp;
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> genericErrorHandler(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}

