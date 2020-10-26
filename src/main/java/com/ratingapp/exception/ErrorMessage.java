package com.ratingapp.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@Data
public class ErrorMessage {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    @NonNull
    private HttpStatus statusCode;
    @NonNull
    private String message;
    List<String> constraintViolations;
    List<String> exceptionMessages;

    private ErrorMessage(){
        timestamp = LocalDateTime.now();
    }

    public ErrorMessage(HttpStatus httpStatus, String message){
        this();
        this.statusCode = httpStatus;
        this.message = message;
    }

    public ErrorMessage(HttpStatus httpStatus, String message, List<String> constraintViolations){
        this();
        this.statusCode = httpStatus;
        this.message = message;
        this.constraintViolations = constraintViolations;
    }
}
