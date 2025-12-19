package mrtn.influ.gateway;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Order(-100) // VERY IMPORTANT
public class WebSocketJwtWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (token != null && token.startsWith("Bearer ")) {
            exchange = exchange.mutate()
                    .request(r -> r.headers(h -> {
                        h.set(HttpHeaders.AUTHORIZATION, token);
                    }))
                    .build();
        }

        return chain.filter(exchange);
    }
}
