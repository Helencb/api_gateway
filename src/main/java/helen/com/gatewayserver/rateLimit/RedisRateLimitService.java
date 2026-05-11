package helen.com.gatewayserver.rateLimit;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisRateLimitService {

    private final ReactiveStringRedisTemplate redisTemplate;

    public Mono<Boolean> isAllowed(String key) {
        return redisTemplate
                .opsForValue()
                .increment(key)
                .flatMap(count -> {
                    if (count == 1) {
                        return redisTemplate
                                .expire(key, Duration.ofSeconds(
                                        RateLimitPolicy.WINDOW_SECONDS))
                                .thenReturn(count);
                    }

                    return Mono.just(count);
                }).map(count ->
                        count <= RateLimitPolicy
                                .REQUESTS_PER_MINUTE);
    }
}
