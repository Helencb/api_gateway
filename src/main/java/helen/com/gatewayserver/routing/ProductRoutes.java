package helen.com.gatewayserver.routing;

public class ProductRoutes {
    private ProductRoutes(){}

    public static final String PATH =
            "/api/product/**";

    public static final String URI =
            "lb://PRODUCT-SERVICE";
}
