package helen.com.gatewayserver.filters.route;

import helen.com.gatewayserver.rateLimit.RedisRateLimitService;
import helen.com.gatewayserver.security.SecurityConstants;
import helen.com.gatewayserver.util.IpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RateLimitFilter {

    private final RedisRateLimitService rateLimitService;

    public GatewayFilter apply() {
        return (exchange, chain) -> {
            String userId = exchange
                    .getRequest()
                    .getHeaders()
                    .getFirst(SecurityConstants.USER_ID);

            if (userId == null) {
                userId = IpUtils.extractIp(exchange);
            }

            String redisKey = "rate-limit:" + userId;

            return rateLimitService.isAllowed(redisKey)
                    .flatMap(allowed -> {
                        if (!allowed) {exchange
                                .getResponse()
                                .setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                            return exchange
                                    .getResponse()
                                    .setComplete();
                        }

                        return chain.filter(
                                exchange
                        );
                    });
        };
    }
}
