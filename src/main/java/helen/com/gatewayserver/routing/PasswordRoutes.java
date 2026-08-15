package helen.com.gatewayserver.routing;

public final class PasswordRoutes {
    private PasswordRoutes(){}

    public static final String PATH =
            "/api/password/**";

    public static final String URI =
            "lb://AUTH-SERVICE";
}
