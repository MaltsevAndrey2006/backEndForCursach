package andrey.dev.backendforcursach.exceptionHandler;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Hidden
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> notFoundByIdExceptionHandler(RuntimeException e) {
        log.error(e.getMessage());
        return errorResponseBuilder(HttpStatus.BAD_REQUEST, e);
    }

    private ResponseEntity<ErrorResponse> errorResponseBuilder(HttpStatus status, Throwable throwable) {
        ErrorResponse errorResponse = new ErrorResponse(status.getReasonPhrase(), throwable.getMessage());
        return ResponseEntity.status(status).body(errorResponse);
    }
}
