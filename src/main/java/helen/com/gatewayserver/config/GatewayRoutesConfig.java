package helen.com.gatewayserver.config;

import helen.com.gatewayserver.filters.route.*;
import helen.com.gatewayserver.routing.*;
import helen.com.gatewayserver.constants.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;

@Configuration
@RequiredArgsConstructor
public class GatewayRoutesConfig {

    private final AuthenticationFilter authenticationFilter;
    private final AuthorizationFilter authorizationFilter;
    private final RequestValidationFilter requestValidationFilter;
    private final HeaderEnrichmentFilter headerEnrichmentFilter;
    private final UserContextFilter userContextFilter;
    private final RateLimitFilter rateLimitFilter;

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(
                        "auth-service",
                        route -> route
                                .path(AuthRoutes.PATH)
                                .filters(filters -> filters
                                        .filter(requestValidationFilter.apply())
                                        .filter(authenticationFilter.apply())
                                        .filter(authorizationFilter.hasRole(SecurityConstants.ROLE_USER))
                                        .filter(userContextFilter.apply())
                                        .filter(headerEnrichmentFilter.apply())
                                        .filter(rateLimitFilter.apply())
                                        .retry(retry -> retry
                                                .setRetries(3))
                                        .circuitBreaker(circuit -> circuit
                                                .setName("authCircuitBreaker")
                                                .setFallbackUri("forward:/fallback/auth")))
                                .uri(AuthRoutes.URI)
                )
                .route(
                        "product-service",
                        route -> route
                                .path("/api/product/**")
                                .filters(filters -> filters
                                        .filter(requestValidationFilter.apply())
                                        .filter(authenticationFilter.apply())
                                        .filter(userContextFilter.apply())
                                        .filter(headerEnrichmentFilter.apply())
                                        .filter(rateLimitFilter.apply())
                                        .retry(retry -> retry
                                                .setRetries(3))
                                        .circuitBreaker(circuit -> circuit
                                                .setName("productCircuitBreaker")
                                                .setFallbackUri("forward:/fallback/products")))
                                .uri(ProductRoutes.URI)
                )
                .route(
                        "cliente-service",
                        route -> route
                                .path(ClientRoutes.PATH)
                                .filters(filters -> filters
                                        .filter(requestValidationFilter.apply())
                                        .filter(authenticationFilter.apply())
                                        .filter(authorizationFilter.hasRole(SecurityConstants.ROLE_ADMIN))
                                        .filter(userContextFilter.apply())
                                        .filter(headerEnrichmentFilter.apply())
                                        .filter(rateLimitFilter.apply())
                                        .retry(retry -> retry
                                                        .setRetries(3))
                                        .circuitBreaker(circuit -> circuit
                                                .setName("clientCircuitBreaker")
                                                .setFallbackUri("forward:/fallback/clients")))
                                .uri(ClientRoutes.URI)
                )
                .route(
                        "order-service",
                        route -> route
                                .path(OrderRoutes.PATH)
                                .filters(filters -> filters
                                        .filter(requestValidationFilter.apply())
                                        .filter(authenticationFilter.apply())
                                        .filter(userContextFilter.apply())
                                        .filter(headerEnrichmentFilter.apply())
                                        .filter(rateLimitFilter.apply())
                                        .retry(retry -> retry
                                                .setRetries(3))
                                        .circuitBreaker(circuit -> circuit
                                                .setName("orderCircuitBreaker")
                                                .setFallbackUri("forward:/fallback/orders")))
                                .uri(OrderRoutes.URI)
                )
                .route(
                        "payment-service",
                        route -> route
                                .path(PaymentRoutes.PATH)
                                .filters(filters -> filters
                                        .filter(requestValidationFilter.apply())
                                        .filter(authenticationFilter.apply())
                                        .filter(userContextFilter.apply())
                                        .filter(headerEnrichmentFilter.apply())
                                        .filter(rateLimitFilter.apply())
                                        .retry(retry -> retry.setRetries(3))
                                        .circuitBreaker(circuit -> circuit
                                                        .setName("paymentCircuitBreaker")
                                                        .setFallbackUri("forward:/fallback/payments")))
                                .uri(PaymentRoutes.URI)
                )
                .route("notification-service",
                        route -> route
                                .path(NotificationRoutes.PATH)
                                .filters(filters -> filters
                                        .filter(requestValidationFilter.apply())
                                        .filter(authenticationFilter.apply())
                                        .filter(userContextFilter.apply())
                                        .filter(headerEnrichmentFilter.apply())
                                        .filter(rateLimitFilter.apply())
                                        .retry(retry -> retry.setRetries(3))
                                        .circuitBreaker(circuit -> circuit
                                                .setName("notificationCircuitBreaker")
                                                .setFallbackUri("forward:/fallback/notifications")))
                                .uri(NotificationRoutes.URI)
                )
                .build();
    }
}
