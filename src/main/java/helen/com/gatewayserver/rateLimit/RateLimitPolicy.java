package helen.com.gatewayserver.rateLimit;

public final class RateLimitPolicy {

    private RateLimitPolicy(){}

    public static final long REQUESTS_PER_MINUTE = 100;
    public static final long WINDOW_SECONDS = 60;
}
