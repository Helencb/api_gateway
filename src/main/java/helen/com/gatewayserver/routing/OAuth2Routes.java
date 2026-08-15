package helen.com.gatewayserver.routing;

public final class OAuth2Routes {
    private OAuth2Routes(){}

    public static final String PATH =
            "/api/oauth2/**";

    public static final String URI =
            "lb://AUTH-SERVICE";
}
