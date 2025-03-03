package mrtn.influ.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

@Configuration
public class SecurityConfig {

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    SecurityWebFilterChain securedEndpointsFilterChain(ServerHttpSecurity http) {
        http
            .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/admin/**")) // Apply only to /admin/**
            .addFilterAt(new JwtAuthenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange((authorize) -> authorize
                    .pathMatchers("/auth/**").permitAll() // Public access
                    .anyExchange().denyAll() // Block everything else
            );

        return http.build();
    }
}
