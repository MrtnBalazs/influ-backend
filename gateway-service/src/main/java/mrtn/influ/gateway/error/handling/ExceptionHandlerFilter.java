package mrtn.influ.gateway.error.handling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

// TODO not working :(
public class ExceptionHandlerFilter implements WebFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            return chain.filter(exchange);
        } catch (GatewayAuthenticationException gatewayAuthenticationException) {
            return Mono.error(gatewayAuthenticationException);
        } catch (TechnicalException technicalException) {
            return Mono.error(technicalException);
        } catch (Exception exception) {
            return Mono.error(exception);
        }
    }
}
