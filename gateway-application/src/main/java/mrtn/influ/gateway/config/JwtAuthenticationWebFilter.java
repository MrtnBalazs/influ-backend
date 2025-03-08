package mrtn.influ.gateway.config;

import mrtn.influ.gateway.error.handling.GatewayBusinessException;
import mrtn.influ.gateway.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;


public class JwtAuthenticationWebFilter implements WebFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationWebFilter.class);

    private final AuthService authService;

    public JwtAuthenticationWebFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        LOGGER.debug("Auth header: {}", authHeader);

        validateAuthHeader(authHeader);
        authService.validateToken(authHeader.substring(7));

        return chain.filter(exchange);
    }

    private void validateAuthHeader(String authHeader) {
        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new GatewayBusinessException("Missing auth token!");
        }
    }



}
