package helen.com.gatewayserver.config;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservabilityConfig {
    private final MeterRegistry meterRegistry;

    public ObservabilityConfig(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void init() {
        meterRegistry.config()
                .commonTags(
                        "application",
                        "gateway-service"
                );
    }
}
