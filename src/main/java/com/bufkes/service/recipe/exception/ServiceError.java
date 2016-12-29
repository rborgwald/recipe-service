package com.bufkes.service.recipe.exception;

import java.util.Arrays;

public class ServiceError {

    private String errorCode;

    private String errorAttribute;

    private String errorMessage;

    private String errorDescription;

    private Object[] messageArguments;

    public ServiceError() {

    }

    public ServiceError(Builder builder) {

        this.errorCode = builder.errorCode;
        this.errorMessage = builder.errorMessage;
        this.errorDescription = builder.errorDescription;
        this.errorAttribute = builder.errorAttribute;
        this.messageArguments = builder.messageArguments;
    }

    public String getErrorCode() {

        return errorCode;
    }

    public void setErrorCode(String errorCode) {

        this.errorCode = errorCode;
    }

    public String getErrorAttribute() {

        return errorAttribute;
    }

    public void setErrorAttribute(String errorAttribute) {

        this.errorAttribute = errorAttribute;
    }

    public String getErrorMessage() {

        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {

        this.errorMessage = errorMessage;
    }

    public String getErrorDescription() {

        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {

        this.errorDescription = errorDescription;
    }

    public Object[] getMessageArguments() {

        return messageArguments;
    }

    public void setMessageArguments(Object[] messageArguments) {

        this.messageArguments = messageArguments;
    }

    @Override
    public String toString() {

        return "ServiceError{" + "errorCode='" + errorCode + '\'' + ", errorAttribute='" + errorAttribute + '\'' + ", errorMessage='"
                + errorMessage + '\'' + ", errorDescription='" + errorDescription + '\'' + ", messageArguments=" + Arrays
                .toString(messageArguments) + '}';
    }

    public static class Builder {

        private String errorCode;

        private String errorAttribute;

        private String errorMessage;

        private String errorDescription;

        private Object[] messageArguments;

        public ServiceError build() {

            return new ServiceError(this);
        }

        public Builder withErrorCode(String errorCode) {

            this.errorCode = errorCode;
            return this;
        }

        public Builder withErrorAttribute(String errorAttribute) {

            this.errorAttribute = errorAttribute;
            return this;
        }

        public Builder withErrorMessage(String errorMessage) {

            this.errorMessage = errorMessage;
            return this;
        }

        public Builder withErrorDescription(String errorDescription) {

            this.errorDescription = errorDescription;
            return this;
        }

        public Builder withMessageArguments(Object... messageArguments) {

            this.messageArguments = messageArguments;
            return this;
        }

    }

}
