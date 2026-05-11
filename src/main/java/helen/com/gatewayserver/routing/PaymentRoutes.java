package helen.com.gatewayserver.routing;

public final class PaymentRoutes {
    private PaymentRoutes(){}

    public static final String PATH =
            "/api/payments/**";

    public static final String URI =
            "lb://PAYMENT-SERVICE";
}
