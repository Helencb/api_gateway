package helen.com.gatewayserver.filters.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RequestResponseGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public reactor.core.publisher.Mono<Void> filter(
            org.springframework.web.server.ServerWebExchange exchange,
            org.springframework.cloud.gateway.filter.GatewayFilterChain chain
    ) {
        return chain.filter(
                exchange
        ).then(reactor.core.publisher.Mono.fromRunnable(() -> {
            var status =
                    exchange
                            .getResponse()
                            .getStatusCode();
            log.info("Response status={}", status);
        }));
    }

    @Override
    public int getOrder() {

        return -70;
    }
}
