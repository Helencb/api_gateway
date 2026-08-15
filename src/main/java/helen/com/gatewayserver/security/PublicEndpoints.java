package helen.com.gatewayserver.security;

import java.util.List;

/**
 * Reference list of gateway-facing paths that don't require a JWT.
 * Not currently wired into SecurityConfig (see the comment there for why) -
 * kept here as documentation of intent / for future reuse if a real
 * ServerAuthenticationConverter is ever added to that chain.
 */
public class PublicEndpoints {
    private PublicEndpoints(){}

    public static final List<String> ROUTES = List.of(
            "/api/auth/**",
            "/api/password/**",
            "/api/oauth2/**",
            "/api/mfa/verify",
            "/actuator/**",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );
}
