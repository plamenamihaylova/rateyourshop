package com.ratingapp.exception;

public class NotFoundEntityException extends RuntimeException {

    public NotFoundEntityException(){}

    public NotFoundEntityException(String errorMessage){
       super(errorMessage);
    }

    public NotFoundEntityException(String errorMessage, Throwable cause){
        super(errorMessage, cause);
    }

    public NotFoundEntityException(Throwable cause){
        super(cause);
    }
}
