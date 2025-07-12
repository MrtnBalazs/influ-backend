package mrtn.influ.gateway.config;

import mrtn.influ.gateway.error.handling.ExceptionHandlerFilter;
import mrtn.influ.gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

import static mrtn.influ.gateway.util.FilterOrderConsts.*;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthService authService;

    @Order(UNSECURED_CHAIN)
    @Bean
    public SecurityWebFilterChain unsecuredEndpointsFilterChain(ServerHttpSecurity http) {
        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable) // TODO check if it's necessary
            .addFilterBefore(new ExceptionHandlerFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/api/v1/auth/**"));
        return http.build();
    }

    @Order(SECURED_CHAIN)
    @Bean
    public SecurityWebFilterChain securedEndpointsFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // TODO check if it's necessary
                .authorizeExchange((authorize) -> authorize
                        .pathMatchers("/api/v1/test/**").permitAll()
                        .pathMatchers("/api/v1/campaigns/**").permitAll()
                        .pathMatchers("/api/v1/users/**").permitAll()
                        .anyExchange().denyAll()
                )
                .addFilterBefore(new ExceptionHandlerFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAt(new JwtAuthenticationWebFilter(authService), SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }
}
