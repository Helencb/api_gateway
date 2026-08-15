package helen.com.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * This chain used to call anyExchange().authenticated() for everything not
     * in PublicEndpoints, but nothing in this app ever populates the reactive
     * SecurityContext (no oauth2ResourceServer/jwt(), no httpBasic, no
     * formLogin - both explicitly disabled below) - so that used to reject
     * EVERY protected route with 401, regardless of a valid JWT being present.
     * Real authentication/authorization already happens per-route via the
     * custom AuthenticationFilter/AuthorizationFilter GatewayFilters wired in
     * GatewayRoutesConfig (they parse and validate the JWT themselves). This
     * chain only needs to disable Spring Security's own login mechanisms so
     * they don't shadow those filters.
     */
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange.anyExchange().permitAll())
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .build();
    }
}
