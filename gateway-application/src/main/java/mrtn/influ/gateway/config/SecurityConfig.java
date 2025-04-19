package mrtn.influ.gateway.config;

import mrtn.influ.gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AuthService authService;

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public SecurityWebFilterChain unsecuredEndpointsFilterChain(ServerHttpSecurity http) {
        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable) // TODO check if it's necessary
            .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/auth/**"));
        return http.build();
    }

    @Bean
    public SecurityWebFilterChain securedEndpointsFilterChain(ServerHttpSecurity http) {
        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable) // TODO check if it's necessary
            .authorizeExchange((authorize) -> authorize
                    .pathMatchers("/api/v1/test/**").permitAll()
                    .pathMatchers("/api/campaigns/**").permitAll()
                    .anyExchange().denyAll()
            )
            .addFilterAt(new JwtAuthenticationWebFilter(authService), SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }
}
