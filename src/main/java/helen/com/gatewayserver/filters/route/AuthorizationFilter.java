package helen.com.gatewayserver.filters.route;

import helen.com.gatewayserver.security.SecurityConstants;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationFilter {

    public GatewayFilter hasRole(String requiredRole) {
        return (exchange, chain) -> {
            String role = exchange
                    .getRequest()
                    .getHeaders()
                    .getFirst(SecurityConstants.USER_ROLE);

            if (!requiredRole.equals(role)) {
                exchange.getResponse()
                        .setStatusCode(HttpStatus.FORBIDDEN);
                return exchange
                        .getResponse()
                        .setComplete();
            }
            return chain.filter(
                    exchange
            );
        };
    }
}
