package salon.ekat.hairStylist.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import salon.ekat.hairStylist.exception.ErrorEntity;
import salon.ekat.hairStylist.exception.HairValidationException;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(HairValidationException.class)
    public ResponseEntity<?> handleHairValidationException(HairValidationException exception) {
        log.warn(exception.getMessage(), exception);

        ErrorEntity errorEntity = new ErrorEntity(
                exception.getClass().getName(),
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleNotValidException(MethodArgumentNotValidException exception) {
        log.warn(exception.getMessage());

        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse(exception.getMessage());

        ErrorEntity errorEntity = new ErrorEntity(
                "MethodArgumentNotValidException",
                message,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException exception) {
        log.warn(exception.getMessage(), exception);

        ErrorEntity errorEntity = new ErrorEntity(
                "IllegalStateException",
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException exception) {
        log.warn(exception.getMessage(), exception);

        ErrorEntity errorEntity = new ErrorEntity(
                "NoSuchElementException",
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorEntity, HttpStatus.NOT_FOUND);
    }
}
