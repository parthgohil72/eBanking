package com.easybytes.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

    public static void main(String[] args) {

        SpringApplication.run(GatewayserverApplication.class, args);
    }

    @Bean
    public RouteLocator eBankingRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(
                        p -> p
                                .path("/ebanking/accounts/**")
                                .filters(f -> f.rewritePath("/ebanking/accounts/(?<segment>.*)","/${segment}")
                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                        .circuitBreaker(config -> config.setName("accountCircuitBreaker")
                                                .setFallbackUri("forward:/contactSupportInfo")))
                                .uri("lb://ACCOUNTS"))
                .route(
                        p -> p
                                .path("/ebanking/cards/**")
                                .filters(f -> f.rewritePath("/ebanking/cards/(?<segment>.*)","/${segment}")
                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                        .retry(retryConfig -> retryConfig.setRetries(3)
                                                .setMethods(HttpMethod.GET)
                                                .setBackoff(Duration.ofMillis(1000),Duration.ofMillis(1000),2,true)))
                                .uri("lb://CARDS"))
                .route(
                        p -> p
                                .path("/ebanking/loans/**")
                                .filters(f -> f.rewritePath("/ebanking/loans/(?<segment>.*)","/${segment}")
                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                                .uri("lb://LOANS")).build();
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5))
                        .build()).build());
    }

}
