package helen.com.gatewayserver.filters.route;

import helen.com.gatewayserver.security.SecurityConstants;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.stereotype.Component;

@Component
public class UserContextFilter {

    public GatewayFilter apply() {
        return (exchange, chain) -> {
            var headers = exchange
                    .getRequest()
                    .getHeaders();

            String userId = headers
                    .getFirst(SecurityConstants.USER_ID);

            String role = headers
                    .getFirst(SecurityConstants.USER_ROLE);

            var request = exchange
                    .getRequest()
                    .mutate()
                    .header("X-Authenticated-User", userId)
                    .header("X-Authenticated-Role", role)
                    .build();

            return chain.filter(exchange
                    .mutate()
                    .request(request)
                    .build());
        };
    }
}
