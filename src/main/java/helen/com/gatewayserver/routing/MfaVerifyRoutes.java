package helen.com.gatewayserver.routing;

/**
 * Narrower, public counterpart of {@link MfaRoutes}: /mfa/verify is called
 * mid-login (before the user has any JWT yet, right after AuthService.login
 * returns mfaRequired=true), so it must NOT go through the authentication
 * filter. Must be registered before MfaRoutes in GatewayRoutesConfig so this
 * more specific path wins.
 */
public final class MfaVerifyRoutes {
    private MfaVerifyRoutes(){}

    public static final String PATH =
            "/api/mfa/verify";

    public static final String URI =
            "lb://AUTH-SERVICE";
}
