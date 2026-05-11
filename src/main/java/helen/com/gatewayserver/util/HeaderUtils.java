package helen.com.gatewayserver.util;

import org.springframework.http.HttpHeaders;

public final class HeaderUtils {
    private HeaderUtils(){}

    public static String getHeader(
            HttpHeaders headers,
            String key) {
        return headers.getFirst(key);
    }

    public static boolean hasHeader(
            HttpHeaders headers,
            String key) {
        return headers.containsKey(key);
    }
}
