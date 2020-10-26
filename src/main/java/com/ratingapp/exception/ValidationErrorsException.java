package com.ratingapp.exception;

import com.ratingapp.model.Shop;
import lombok.*;
import org.springframework.validation.Errors;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Data
@AllArgsConstructor
public class ValidationErrorsException extends RuntimeException {

    private Errors errors;
    private Set<ConstraintViolation<?>> violations;

    public ValidationErrorsException() {
    }

    public ValidationErrorsException(String message) {
        super(message);
    }

    public ValidationErrorsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationErrorsException(Throwable cause) {
        super(cause);
    }

    public ValidationErrorsException(Errors errors) {
        this.errors = errors;
    }

    public ValidationErrorsException(Set<ConstraintViolation<?>> violations) {
        this.violations = violations;
    }

}
