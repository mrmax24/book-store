package mate.academy.bookstore.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String TIMESTAMP_LABEL = "timestamp";
    private static final String STATUS_LABEL = "status";
    private static final String ERRORS_LABEL = "errors";
    private static final String ERROR_LABEL = "error";
    private static final String MESSAGE_LABEL = "message";
    private static final String FIELD_LABEL = "' field ";
    private static final String DELIMITER_LABEL = "'";
    private static final String REPLACEMENT_LABEL = "$1 $2";
    private static final String REGISTRATION_ERROR_MESSAGE = "Registration error";
    private static final String ENTITY_NOT_FOUND_ERROR_MESSAGE = "Entity not found error";
    private static final DateTimeFormatter FORMATTER
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_LABEL, LocalDateTime.now().format(FORMATTER));
        body.put(STATUS_LABEL, HttpStatus.BAD_REQUEST);

        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(this::getErrorMessageForArgumentNotValid)
                .toList();

        body.put(ERRORS_LABEL, List.of(errors));
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(RegistrationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(
            RegistrationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_LABEL, LocalDateTime.now().format(FORMATTER));
        body.put(STATUS_LABEL, HttpStatus.BAD_REQUEST);
        body.put(ERROR_LABEL, REGISTRATION_ERROR_MESSAGE);
        body.put(MESSAGE_LABEL, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(
            EntityNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_LABEL, LocalDateTime.now().format(FORMATTER));
        body.put(STATUS_LABEL, HttpStatus.NOT_FOUND);
        body.put(ERROR_LABEL, ENTITY_NOT_FOUND_ERROR_MESSAGE);
        body.put(MESSAGE_LABEL, ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    private String getErrorMessageForArgumentNotValid(ObjectError e) {
        if (e instanceof FieldError) {
            String field = ((FieldError) e).getField();
            String formattedField = StringUtils.capitalize(
                    field.replaceAll("(\\p{Ll})(\\p{Lu})", REPLACEMENT_LABEL));
            return DELIMITER_LABEL + formattedField + FIELD_LABEL + e.getDefaultMessage();
        }
        return e.getDefaultMessage();
    }
}
