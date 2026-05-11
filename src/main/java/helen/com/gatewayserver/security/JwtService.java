package helen.com.gatewayserver.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {
    @Value("${security.jwt.secret}")
    private String secret;

    public Claims extractClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserId(String token){
        return extractClaims(token)
                .getSubject();
    }

    public String extractRole(String token){
        return extractClaims(token)
            .get("role", String.class);
    }
}
