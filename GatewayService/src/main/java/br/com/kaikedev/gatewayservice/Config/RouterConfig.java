package br.com.kaikedev.gatewayservice.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {

    @Value("${internal.endpoint.product-service}")
    private String productEndpoint;

    @Value("${internal.endpoint.order-service}")
    private String orderEndpoint;

    @Value("${internal.endpoint.user-service}")
    private String userEndpoint;

    @Value("${internal.endpoint.email-service}")
    private String emailEndpoint;

    @Value("${internal.endpoint.logistic-service}")
    private String logisticEndpoint;

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("rota-product", r -> r
                        .path("/v1/product-service/**")
                        .filters(f -> f.rewritePath("/v1/product-service/(?<remaining>.*)", "/${remaining}"))
                        .uri(productEndpoint)
                )

                .route("rota-order", r -> r
                        .path("/v1/order-service/**")
                        .filters(f -> f.rewritePath("/v1/order-service/(?<remaining>.*)", "/${remaining}"))
                        .uri(orderEndpoint)
                )

                .route("rota-user", r -> r
                        .path("/v1/user-service/**")
                        .filters(f -> f.rewritePath("/v1/user-service/(?<remaining>.*)", "/${remaining}"))
                        .uri(userEndpoint)
                )

                .route("rota-email", r -> r
                        .path("/v1/email-service/**")
                        .filters(f -> f.rewritePath("/v1/email-service/(?<remaining>.*)", "/${remaining}"))
                        .uri(emailEndpoint)
                )

                .route("rota-logistic", r -> r
                        .path("/v1/logistic-service/**")
                        .filters(f -> f.rewritePath("/v1/logistic-service/(?<remaining>.*)", "/${remaining}"))
                        .uri(logisticEndpoint)
                )

                .build();
    }
}
