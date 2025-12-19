package mrtn.influ.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class WebSocketAuthHeaderFilter
        extends AbstractGatewayFilterFactory<WebSocketAuthHeaderFilter.Config> {

    public WebSocketAuthHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            System.out.println("websocket filter activated");
//            if (!"websocket".equalsIgnoreCase(exchange.getRequest().getURI().getScheme())
//                    && !"ws".equalsIgnoreCase(exchange.getRequest().getURI().getScheme())) {
//                return chain.filter(exchange);
//            }

            String token = exchange.getRequest().getQueryParams().getFirst("token");
            if (token != null) {
                return chain.filter(
                        exchange.mutate()
                                .request(r -> r.headers(h -> {
                                    h.set("Authorization", token);
                                }))
                                .build()
                );
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
    }
}
