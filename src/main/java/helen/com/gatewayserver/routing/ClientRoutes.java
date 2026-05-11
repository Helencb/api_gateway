package helen.com.gatewayserver.routing;

public final class ClientRoutes {
    private ClientRoutes(){}

    public static final String PATH =
            "/api/clients/**";

    public static final String URI =
            "lb://CLIENT-SERVICE";
}

