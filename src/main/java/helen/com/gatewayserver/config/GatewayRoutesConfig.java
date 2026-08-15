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
                // /auth/register, /auth/login and /auth/refresh are public by design
                // (there is no JWT yet) - this route must NOT go through
                // authenticationFilter/authorizationFilter, unlike the other
                // downstream services below.
                .route(
                        "auth-service",
                        route -> route
                                .path(AuthRoutes.PATH)
                                .filters(filters -> filters
                                        .filter(requestValidationFilter.apply())
                                        .filter(headerEnrichmentFilter.apply())
                                        .filter(rateLimitFilter.apply())
                                        .stripPrefix(1)
                                        .retry(retry -> retry
                                                .setRetries(3))
                                        .circuitBreaker(circuit -> circuit
                                                .setName("authCircuitBreaker")
                                                .setFallbackUri("forward:/fallback/auth")))
                                .uri(AuthRoutes.URI)
                )
                // Password reset ("esqueci minha senha") is also public - the whole
                // point is to recover access without being logged in.
                .route(
                        "password-service",
                        route -> route
                                .path(PasswordRoutes.PATH)
                                .filters(filters -> filters
                                        .filter(requestValidationFilter.apply())
                                        .filter(headerEnrichmentFilter.apply())
                                        .filter(rateLimitFilter.apply())
                                        .stripPrefix(1)
                                        .retry(retry -> retry
                                                .setRetries(3))
                                        .circuitBreaker(circuit -> circuit
                                                .setName("authCircuitBreaker")
                                                .setFallbackUri("forward:/fallback/auth")))
                                .uri(PasswordRoutes.URI)
                )
                // OAuth2 social login redirect/callback dance happens before the
                // caller has a JWT - must stay public too.
                .route(
                        "oauth2-service",
                        route -> route
                                .path(OAuth2Routes.PATH)
                                .filters(filters -> filters
                                        .filter(requestValidationFilter.apply())
                                        .filter(headerEnrichmentFilter.apply())
                                        .filter(rateLimitFilter.apply())
                                        .stripPrefix(1)
                                        .retry(retry -> retry
                                                .setRetries(3))
                                        .circuitBreaker(circuit -> circuit
                                                .setName("authCircuitBreaker")
                                                .setFallbackUri("forward:/fallback/auth")))
                                .uri(OAuth2Routes.URI)
                )
                // /mfa/verify is called mid-login (after AuthService.login returns
                // mfaRequired=true, before any JWT exists) so it's public too. Must
                // be declared before "mfa-service" below so this more specific path
                // wins the route match.
                .route(
                        "mfa-verify-service",
                        route -> route
                                .path(MfaVerifyRoutes.PATH)
                                .filters(filters -> filters
                                        .filter(requestValidationFilter.apply())
                                        .filter(headerEnrichmentFilter.apply())
                                        .filter(rateLimitFilter.apply())
                                        .stripPrefix(1)
                                        .retry(retry -> retry
                                                .setRetries(3))
                                        .circuitBreaker(circuit -> circuit
                                                .setName("authCircuitBreaker")
                                                .setFallbackUri("forward:/fallback/auth")))
                                .uri(MfaVerifyRoutes.URI)
                )
                // /mfa/setup and /mfa/enable require an authenticated user (the
                // controller reads it from Authentication) - protected like the
                // other downstream services.
                .route(
                        "mfa-service",
                        route -> route
                                .path(MfaRoutes.PATH)
                                .filters(filters -> filters
                                        .filter(requestValidationFilter.apply())
                                        .filter(authenticationFilter.apply())
                                        .filter(authorizationFilter.hasRole(SecurityConstants.ROLE_USER))
                                        .filter(userContextFilter.apply())
                                        .filter(headerEnrichmentFilter.apply())
                                        .filter(rateLimitFilter.apply())
                                        .stripPrefix(1)
                                        .retry(retry -> retry
                                                .setRetries(3))
                                        .circuitBreaker(circuit -> circuit
                                                .setName("authCircuitBreaker")
                                                .setFallbackUri("forward:/fallback/auth")))
                                .uri(MfaRoutes.URI)
                )
                .route(
                        "session-service",
                        route -> route
                                .path(SessionRoutes.PATH)
                                .filters(filters -> filters
                                        .filter(requestValidationFilter.apply())
                                        .filter(authenticationFilter.apply())
                                        .filter(authorizationFilter.hasRole(SecurityConstants.ROLE_USER))
                                        .filter(userContextFilter.apply())
                                        .filter(headerEnrichmentFilter.apply())
                                        .filter(rateLimitFilter.apply())
                                        .stripPrefix(1)
                                        .retry(retry -> retry
                                                .setRetries(3))
                                        .circuitBreaker(circuit -> circuit
                                                .setName("authCircuitBreaker")
                                                .setFallbackUri("forward:/fallback/auth")))
                                .uri(SessionRoutes.URI)
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
                .route("saga-orchestrator-service",
                        route -> route
                                .path(SagaRoutes.PATH)
                                .filters(filters -> filters
                                        .filter(requestValidationFilter.apply())
                                        .filter(authenticationFilter.apply())
                                        .filter(authorizationFilter.hasRole(SecurityConstants.ROLE_ADMIN))
                                        .filter(userContextFilter.apply())
                                        .filter(headerEnrichmentFilter.apply())
                                        .filter(rateLimitFilter.apply())
                                        .retry(retry -> retry.setRetries(3))
                                        .circuitBreaker(circuit -> circuit
                                                .setName("sagaCircuitBreaker")
                                                .setFallbackUri("forward:/fallback/sagas")))
                                .uri(SagaRoutes.URI)
                )
                .build();
    }
}
