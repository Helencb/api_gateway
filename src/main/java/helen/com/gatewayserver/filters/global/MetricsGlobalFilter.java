package helen.com.gatewayserver.filters.global;

import helen.com.gatewayserver.constants.GatewayConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MetricsGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public reactor.core.publisher.Mono<Void> filter(
            org.springframework.web.server.ServerWebExchange exchange,
            org.springframework.cloud.gateway.filter.GatewayFilterChain chain
    ) {
        long start = System.currentTimeMillis();
        exchange.getAttributes()
                .put(GatewayConstants.REQUEST_START_TIME, start);

        return chain.filter(exchange).then(
                reactor.core.publisher.Mono.fromRunnable(() -> {
                    long end = System.currentTimeMillis();
                    long duration = end - start;
                    log.info( "Latency={}ms path={}",
                            duration,
                            exchange
                                    .getRequest()
                                    .getURI()
                                    .getPath());
                })
        );
    }

    @Override
    public int getOrder() {
        return -80;
    }
}
