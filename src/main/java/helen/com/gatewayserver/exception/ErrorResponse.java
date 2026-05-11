package helen.com.gatewayserver.exception;

import java.time.LocalDateTime;

public record ErrorResponse (
        LocalDateTime timestamp,
        int status,
        String message
){
}
