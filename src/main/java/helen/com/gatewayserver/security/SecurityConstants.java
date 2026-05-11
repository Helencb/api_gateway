package helen.com.gatewayserver.security;

public final class SecurityConstants {
    private SecurityConstants(){}

    public static final String AUTHORIZATION =
            "Authorization";
    public static final String BEARER =
            "Bearer ";
    public static final String USER_ID =
            "X-User-Id";
    public static final String USER_ROLE =
            "X-User-Role";
    public static final String ROLE_ADMIN =
            "ADMIN";
    public static final String ROLE_USER =
            "USER";
}
