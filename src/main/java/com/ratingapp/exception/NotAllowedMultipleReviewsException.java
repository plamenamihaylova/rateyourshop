package com.ratingapp.exception;

public class NotAllowedMultipleReviewsException extends RuntimeException{
    public NotAllowedMultipleReviewsException(){}

    public NotAllowedMultipleReviewsException(String message){
        super(message);
    }

    public NotAllowedMultipleReviewsException(String message, Throwable cause){
        super(message, cause);
    }

    public NotAllowedMultipleReviewsException(Throwable cause){
        super(cause);
    }
}
