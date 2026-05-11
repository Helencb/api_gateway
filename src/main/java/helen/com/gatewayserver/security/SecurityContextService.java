package helen.com.gatewayserver.security;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService {
    public ServerHttpRequest enrichRequest(
            ServerHttpRequest request,
            String userId,
            String role
    ){
        return request
                .mutate()
                .header("X-User-Id", userId)
                .header("X-User-Role", role)
                .build();
    }
}
