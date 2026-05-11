package helen.com.gatewayserver.observability;

import helen.com.gatewayserver.constants.GatewayConstants;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

@Service
public class TracingService {

    public String extractCorrelationId(
            ServerWebExchange exchange
    ) {
        return exchange
                .getRequest()
                .getHeaders()
                .getFirst(GatewayConstants.CORRELATION_ID);
    }
}
