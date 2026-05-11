package helen.com.gatewayserver.filters.global;

import helen.com.gatewayserver.constants.GatewayConstants;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CorrelationIdGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public reactor.core.publisher.Mono<Void> filter(
            org.springframework.web.server.ServerWebExchange exchange,
            org.springframework.cloud.gateway.filter.GatewayFilterChain chain
    ) {
        String correlationId = UUID.randomUUID().toString();

        var request = exchange
                .getRequest()
                .mutate()
                .header(GatewayConstants.CORRELATION_ID, correlationId)
                .build();

        return chain.filter(exchange
                .mutate()
                .request(request)
                .build());
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
