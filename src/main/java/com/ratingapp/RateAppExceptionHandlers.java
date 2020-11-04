package com.ratingapp;

import com.ratingapp.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice // in order to make the class listen for the exceptions that take place in my application
public class RateAppExceptionHandlers extends ResponseEntityExceptionHandler {

    public static final String VALIDATION_EXCEPTION_ERROR_MESSAGE = "Validation error found.";
    public static final String DATA_INTEGRITY_EXCEPTION_ERROR_MESSAGE = "Data integrity violation.";
    public static final Integer NOT_FOUND_STATUS_CODE = 404;

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFound(NotFoundEntityException exception){
        return ResponseEntity.status(NOT_FOUND_STATUS_CODE).body(new ErrorMessage(NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(InvalidEntityDataException.class)
    public ResponseEntity<ErrorMessage> handleInvalidEntityDataException(InvalidEntityDataException exception){
        return ResponseEntity.badRequest().body(new ErrorMessage(BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(value = {ValidationErrorsException.class})
    public ResponseEntity<ErrorMessage> handleValidationErrorsException(ValidationErrorsException exception){
        ErrorMessage errorMessage = new ErrorMessage(BAD_REQUEST, VALIDATION_EXCEPTION_ERROR_MESSAGE, new ArrayList<>());

        exception.getErrors().getAllErrors().forEach(objectError -> {
            ConstraintViolation constraintViolation = objectError.unwrap(ConstraintViolation.class);
            String violation = String.format("%s's field '%s' with value '%s', %s", constraintViolation.getLeafBean().getClass().getSimpleName(),
                                                        constraintViolation.getPropertyPath(),
                                                        constraintViolation.getInvalidValue(),
                                                        constraintViolation.getMessage());
            errorMessage.getConstraintViolations().add(violation);
        });

        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ResponseStatus(value = CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException(DataIntegrityViolationException exception){
        ErrorMessage errorMessage = new ErrorMessage(CONFLICT, DATA_INTEGRITY_EXCEPTION_ERROR_MESSAGE, new ArrayList<>());
        errorMessage.getConstraintViolations().add(exception.getRootCause().getMessage());
        return ResponseEntity.badRequest().body(errorMessage);
    }

}
