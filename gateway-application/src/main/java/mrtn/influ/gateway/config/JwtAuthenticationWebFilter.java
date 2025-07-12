package mrtn.influ.gateway.config;

import mrtn.influ.gateway.error.handling.GatewayAuthenticationException;
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
    public static final String USER_ID_HEADER = "X-User-Id";

    private final AuthService authService;

    public JwtAuthenticationWebFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        LOGGER.debug("Auth header: {}", authHeader);

        validateAuthHeader(authHeader);
        String userId = authService.validateToken(authHeader.substring(7));
        if(Objects.nonNull(userId))
            exchange.getRequest().mutate().header(USER_ID_HEADER, userId);

        return chain.filter(exchange);
    }

    private void validateAuthHeader(String authHeader) {
        if (Objects.isNull(authHeader))
            throw new GatewayAuthenticationException("Missing auth header!");
        if (!authHeader.startsWith("Bearer "))
            throw new GatewayAuthenticationException("Missing bearer token!");
    }
}
