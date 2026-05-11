package helen.com.gatewayserver.observability;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MetricsService {
    private final MeterRegistry registry;

    public MetricsService(MeterRegistry registry) {
        this.registry = registry;
    }

    public void incrementRequest(String path) {
        Counter.builder("gateway.requests.total")
                .tag("path", path)
                .register(registry)
                .increment();
    }

    public void incrementError(String path) {
        Counter.builder("gateway.errors.total")
                .tag("path", path)
                .register(registry)
                .increment();
    }
    public void recordLatency(String path, long duration) {
        Timer.builder("gateway.request.duration")
                .tag("path", path)
                .register(registry)
                .record(duration, TimeUnit.MILLISECONDS);
    }
}
