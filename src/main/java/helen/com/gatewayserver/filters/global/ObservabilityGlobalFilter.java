package helen.com.gatewayserver.filters.global;

import helen.com.gatewayserver.observability.MetricsService;
import helen.com.gatewayserver.observability.TracingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ObservabilityGlobalFilter implements GlobalFilter, Ordered {

    private final MetricsService metricsService;
    private final TracingService tracingService;

    public ObservabilityGlobalFilter(MetricsService metricsService, TracingService tracingService) {
        this.metricsService = metricsService;
        this.tracingService = tracingService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange
                .getRequest()
                .getURI()
                .getPath();

        String correlationId = tracingService.extractCorrelationId(exchange);

        long start = System.currentTimeMillis();
        metricsService.incrementRequest(path);

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    long duration = System.currentTimeMillis()
                            - start;

                    metricsService.recordLatency(
                            path, duration);

                    var status = exchange
                            .getResponse()
                            .getStatusCode();

                    if (status != null && status.isError()) {
                        metricsService.incrementError(path);
                    }

                    log.info("correlationId={} path={} status={} duration={}ms",
                            correlationId,
                            path,
                            status,
                            duration
                    );
                }));
    }
    @Override
    public int getOrder() {
        return -60;
    }
}
