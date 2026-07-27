package helen.com.gatewayserver.routing;

public final class SagaRoutes {
    private SagaRoutes(){}

    public static final String PATH =
            "/api/sagas/**";

    public static final String URI =
            "lb://SAGA-ORCHESTRATOR-SERVICE";
}
