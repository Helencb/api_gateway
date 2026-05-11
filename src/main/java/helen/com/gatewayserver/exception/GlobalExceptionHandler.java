package helen.com.gatewayserver.exception;

import helen.com.gatewayserver.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(
            Exception ex
    ) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                500,
                ex.getMessage());

        log.error("Gateway exception {}", JsonUtils.toJson(response));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
