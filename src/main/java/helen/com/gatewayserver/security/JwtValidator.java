package helen.com.gatewayserver.security;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {
    private final JwtService jwtService;

    public  JwtValidator(JwtService jwtService){
        this.jwtService = jwtService;
    }

    public boolean isValid(String token) {
        try {
            Claims claims = jwtService.extractClaims(token);
            return claims != null;
        } catch (Exception e){
            return false;
        }
    }
}
