package helen.com.gatewayserver.fallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class AuthFallbackController {
    @GetMapping("/auth")
    public ResponseEntity<Map<String,Object>> fallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "service", "AUTH-SERVICE",
                        "status", 503,
                        "message", "Service temporarily unavailable"));
    }
}
