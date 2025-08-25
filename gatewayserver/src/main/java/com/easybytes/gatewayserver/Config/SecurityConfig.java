package com.easybytes.gatewayserver.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;

@ControllerAdvice
@EnableWebFlux
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {

        serverHttpSecurity.authorizeExchange(exchange -> exchange.pathMatchers(HttpMethod.GET).permitAll()
                .pathMatchers("/ebanking/accounts/**").hasRole("ACCOUNTS")
                .pathMatchers("/ebanking/cards/**").hasRole("CARDS")
                .pathMatchers("/ebanking/loans/**").hasRole("LOANS"))
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.
                        jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())));
        serverHttpSecurity.csrf(csrfSpec ->  csrfSpec.disable());
        return serverHttpSecurity.build();
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
