package helen.com.gatewayserver.filters.route;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.stereotype.Component;

@Component
public class HeaderEnrichmentFilter {

    public GatewayFilter apply() {
        return (exchange, chain) -> {
            var request = exchange
                    .getRequest()
                    .mutate()
                    .header("X-Gateway-Version",
                            "1.0")
                    .build();
            return chain.filter(exchange
                    .mutate()
                    .request(request)
                    .build()
            );
        };
    }
}
