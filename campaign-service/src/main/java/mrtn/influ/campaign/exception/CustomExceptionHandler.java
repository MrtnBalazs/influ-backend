package mrtn.influ.campaign.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<Object> handleBusinessExceptions(BusinessException businessException) {
        LOGGER.error("Business exception caught", businessException);
        return ResponseEntity
                .status(businessException.getErrorCode().getHttpStatus())
                .body(businessException.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException validationException) {
        LOGGER.error("Validation exception caught", validationException);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(validationException.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception exception) {
        LOGGER.error("Exception caught", exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}
