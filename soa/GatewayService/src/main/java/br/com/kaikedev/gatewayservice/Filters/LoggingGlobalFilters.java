package br.com.kaikedev.gatewayservice.Filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@Component
public class LoggingGlobalFilters implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingGlobalFilters.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest requestPath = exchange.getRequest();
        Instant startTime = Instant.now();
        String clientIp = Objects.requireNonNull(requestPath.getRemoteAddress()).toString();
        String method = requestPath.getMethod().name();
        String path = requestPath.getURI().getPath();
        
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {

            Duration duration = Duration.between(startTime, Instant.now());
            ServerHttpResponse responsePath = exchange.getResponse();
            Integer statusCode = Objects.requireNonNull(responsePath.getStatusCode()).value();

            logger.info("Client IP: {} - Method: {} - Path: {} - Status: {} - Duration: {} ms",
                    clientIp, method, path, statusCode, duration.toMillis());

        }));
    }
}
