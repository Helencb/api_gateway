package helen.com.gatewayserver.security;

import java.util.List;

public class PublicEndpoints {
    private PublicEndpoints(){}

    public static final List<String> ROUTES = List.of(
            "/auth/**",
            "/actuator/**",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    );
}
