package helen.com.gatewayserver.routing;

public final class MfaRoutes {
    private MfaRoutes(){}

    public static final String PATH =
            "/api/mfa/**";

    public static final String URI =
            "lb://AUTH-SERVICE";
}
