package helen.com.gatewayserver.routing;

public final class NotificationRoutes {
    private NotificationRoutes(){}

    public static final String PATH =
            "/api/notifications/**";

    public static final String URI =
            "lb://NOTIFICATION-SERVICE";
}
