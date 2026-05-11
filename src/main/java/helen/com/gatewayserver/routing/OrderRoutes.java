package helen.com.gatewayserver.routing;

public final class OrderRoutes {
    private OrderRoutes(){}

    public static final String PATH =
            "/api/orders/**";

    public static final String URI =
            "lb://ORDER-SERVICE";
}
