package helen.com.gatewayserver.filters.route;

import helen.com.gatewayserver.constants.GatewayConstants;
import helen.com.gatewayserver.util.HeaderUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RequestValidationFilter {

    public GatewayFilter apply() {
        return ((exchange, chain) -> {
            var headers = exchange
                    .getRequest()
                    .getHeaders();

            boolean exists = HeaderUtils.hasHeader(headers, GatewayConstants.CLIENT_APP);

            if (!exists) {
                exchange.getResponse()
                        .setStatusCode(HttpStatus.BAD_REQUEST);

                return exchange
                        .getResponse()
                        .setComplete();
            }

            return chain.filter(exchange);
        });
    }
}
