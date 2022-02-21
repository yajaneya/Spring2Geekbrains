package ru.yajaneya.Spring2Geekbrains.core.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.AppError;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.CoreServiceAppError;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.ResourceNotFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CoreServiceAppError(
                CoreServiceAppError.CoreServiceErrors.PRODUCT_NOT_FOUND.name(),
                e.getMessage()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> catchCartServiceIntegrationException(CartServiceIntegrationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CoreServiceAppError(
                CoreServiceAppError.CoreServiceErrors.CART_SERVICE_INTEGRATION_ERROR.name(),
                e.getMessage()
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<FieldsValidationError> catchValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new FieldsValidationError(e.getErrorFieldMessages()), HttpStatus.BAD_REQUEST);
    }

}
