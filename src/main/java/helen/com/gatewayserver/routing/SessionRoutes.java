package helen.com.gatewayserver.routing;

public final class SessionRoutes {
    private SessionRoutes(){}

    public static final String PATH =
            "/api/sessions/**";

    public static final String URI =
            "lb://AUTH-SERVICE";
}
