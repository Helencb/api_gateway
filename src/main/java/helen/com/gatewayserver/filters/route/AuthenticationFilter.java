package helen.com.gatewayserver.filters.route;

import helen.com.gatewayserver.security.JwtService;
import helen.com.gatewayserver.security.JwtValidator;
import helen.com.gatewayserver.constants.SecurityConstants;
import helen.com.gatewayserver.security.SecurityContextService;
import helen.com.gatewayserver.util.HeaderUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter {
    private final JwtValidator validator;
    private final JwtService jwtService;
    private final SecurityContextService contextService;

    public AuthenticationFilter(JwtValidator validator, JwtService jwtService, SecurityContextService contextService) {
        this.validator = validator;
        this.jwtService = jwtService;
        this.contextService = contextService;
    }

    public GatewayFilter apply() {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange
                    .getRequest()
                    .getHeaders();

            if (!HeaderUtils.hasHeader(headers,
                    SecurityConstants.AUTHORIZATION)) {
                exchange.getResponse()
                        .setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse()
                        .setComplete();
            }

            String authorization = HeaderUtils
                    .getHeader(headers, SecurityConstants.AUTHORIZATION);

            if (authorization == null || authorization.isBlank()) {
                exchange.getResponse()
                        .setStatusCode(HttpStatus.UNAUTHORIZED);

                return exchange.getResponse()
                        .setComplete();
            }

            String token = authorization.replace(SecurityConstants.BEARER, "");

            if (!validator.isValid(token)) {
                exchange.getResponse()
                        .setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange
                        .getResponse()
                        .setComplete();
            }

            String userId = jwtService.extractUserId(token);
            String role = jwtService.extractRole(token);

            var request = contextService.enrichRequest(
                    exchange.getRequest(),
                    userId,
                    role);

            return chain.filter(exchange
                    .mutate()
                    .request(request)
                    .build());
        };
    }
}
