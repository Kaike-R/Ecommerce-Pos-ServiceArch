package br.com.kaikedev.gateway.Configs;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.UUID;
import java.util.function.Function;
import java.util.logging.Logger;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.prefixPath;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.stripPrefix;

import org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates;
import org.springframework.cloud.gateway.server.mvc.handler.*;

@Configuration
public class GatewayConfigurations {



    @Bean
    public RouterFunction<ServerResponse> handleRoutes() {

        return GatewayRouterFunctions.route("route_id")
                //.before(stripPrefix(1))
                .before( req -> {
                        System.out.println("Incoming request - Method: " + req.method() + " | Path: " + req.path());
                        return req;
                }
                )
                .route(
                        GatewayRequestPredicates.path("/get")
                        .and(GatewayRequestPredicates.method(HttpMethod.GET)),
                        HandlerFunctions.http("http://httpbin.org:80")
                )
                .route(
                        GatewayRequestPredicates.path("/v1/auth/login")
                        .and(GatewayRequestPredicates.method(HttpMethod.GET)),
                        HandlerFunctions.forward("/bearer")
                        //HandlerFunctions.http("http://httpbin.org:80")
                )
                .route(
                        GatewayRequestPredicates.path("/bearer")
                        .and(GatewayRequestPredicates.method(HttpMethod.GET)),
                        HandlerFunctions.http("http://httpbin.org:80")
                )
                .route(
                        GatewayRequestPredicates.path("/post")
                                .and(GatewayRequestPredicates.method(HttpMethod.GET))
                                .or(GatewayRequestPredicates.method(HttpMethod.DELETE)),
                        HandlerFunctions.http("http://httpbin.org:80")
                )
                .route(
                        GatewayRequestPredicates.path("/teste")
                                .and(GatewayRequestPredicates.method(HttpMethod.GET))
                                HandlerFunctions.http("http://localhost:8080")
                )
                .onError(Exception.class, this::handleException)
                .after((req, res) -> {
                    System.out.println(req.toString());
                    System.out.println("Requisição: " /*+ req.uri()*/ + " " + req.path() + " " +  res.statusCode());
                    return res;
                })
                .build();
    }

    private ServerResponse handleException(Throwable throwable, ServerRequest serverRequest) {
        System.out.println("Erro: " + throwable.getMessage());
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(throwable.getMessage());
    }
}
