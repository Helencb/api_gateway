package helen.com.gatewayserver.util;

import org.springframework.web.server.ServerWebExchange;

public final class IpUtils {

    private IpUtils(){}

    public static String extractIp(ServerWebExchange exchange) {
        return exchange
                .getRequest()
                .getRemoteAddress()
                .getAddress()
                .getHostAddress();
    }
}
