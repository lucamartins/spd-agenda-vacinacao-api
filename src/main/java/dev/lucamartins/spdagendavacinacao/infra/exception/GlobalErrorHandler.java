package dev.lucamartins.spdagendavacinacao.infra.exception;

import dev.lucamartins.spdagendavacinacao.infra.exception.custom.BadRequestException;
import dev.lucamartins.spdagendavacinacao.infra.exception.custom.CustomExceptionBody;
import dev.lucamartins.spdagendavacinacao.infra.exception.custom.ForbiddenException;
import dev.lucamartins.spdagendavacinacao.infra.exception.custom.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomExceptionBody> handleBadRequestException(BadRequestException exception) {
        var badRequestDetails = new CustomExceptionBody(
                HttpStatus.BAD_REQUEST.value(),
                exception.getErrorMessages()
        );

        return ResponseEntity.badRequest().body(badRequestDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionBody> handleValidationsExceptions(MethodArgumentNotValidException ex) {
        var fieldErrors = ex.getFieldErrors();

        var errorMessages = fieldErrors.stream()
                .map(fieldError -> {
                    var field = fieldError.getField();
                    var message = fieldError.getDefaultMessage();

                    return String.format("%s: %s", field, message);
                })
                .toList();

        var badRequestDetails = new CustomExceptionBody(
                HttpStatus.BAD_REQUEST.value(),
                errorMessages
        );

        return ResponseEntity.badRequest().body(badRequestDetails);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomExceptionBody> handleValidationsExceptions1(HttpMessageNotReadableException ex) {
        var badRequestDetails = new CustomExceptionBody(
                HttpStatus.BAD_REQUEST.value(),
                List.of("Invalid request. Check the URL, body fields and try again.")
        );

        return ResponseEntity.badRequest().body(badRequestDetails);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomExceptionBody> handleValidationsExceptions2(MethodArgumentTypeMismatchException ex) {
        var badRequestDetails = new CustomExceptionBody(
                HttpStatus.BAD_REQUEST.value(),
                List.of("Invalid request. Check the URL, body fields and try again.")
        );

        return ResponseEntity.badRequest().body(badRequestDetails);
    }


    @ExceptionHandler({NotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<Void> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CustomExceptionBody> handleForbiddenException(ForbiddenException exception) {

        var excMsg = exception.getMessage();

        var customExceptionBody = new CustomExceptionBody(
                HttpStatus.FORBIDDEN.value(),
                excMsg != null ? List.of(excMsg) : null
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(customExceptionBody);
    }

}
