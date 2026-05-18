package helen.com.gatewayserver.routing;

public final class AuthRoutes {
    private AuthRoutes(){}

    public static final String PATH =
            "/api/auth/**";

    public static final String URI =
            "lb://AUTH-SERVICE";
}
