package mrtn.influ.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


public class JwtAuthenticationWebFilter implements WebFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationWebFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String username = exchange.getRequest().getHeaders().getFirst("username");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException();
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("jwt", authHeader.substring(7));
        headers.set("username", username);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "http://localhost:8082/auth/verify";
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.GET, entity, Void.class);
        if(!response.getStatusCode().is2xxSuccessful())
            throw new RuntimeException();

        return chain.filter(exchange);
    }

}
