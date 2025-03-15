package brvnxc.com.git.spring_security_api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Unhandled exception: ", ex);

        HttpStatus httpCode = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse error = new ErrorResponse(
                httpCode.value(),
                "An unexpected error has occurred, contact the administrator",
                request.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpStatus httpCode = HttpStatus.BAD_REQUEST;

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponse error = new ErrorResponse(httpCode.value(), errors.toString(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        HttpStatus httpCode = HttpStatus.NOT_FOUND;
        ErrorResponse error = new ErrorResponse(httpCode.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(error, httpCode);
    }

    @ExceptionHandler(LoginFiledException.class)
    public ResponseEntity<ErrorResponse> handleLoginFiledExceptionException(LoginFiledException ex, WebRequest request) {
        HttpStatus httpCode = HttpStatus.FORBIDDEN;
        ErrorResponse error = new ErrorResponse(httpCode.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(error, httpCode);
    }
}
