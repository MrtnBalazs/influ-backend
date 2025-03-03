package mrtn.influ.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ApiGatewayApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiGatewayApplication.class);

    @Autowired
    @Qualifier("webClientBean")
    private WebClient webClient;


    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("test", r -> r.path("/test/")
                        .uri("lb://TEST-SERVICE"))
                .route("auth", r -> r.path("/auth/**", "/admin/**")
                        .uri("lb://AUTH-SERVICE"))
                .build();
    }
}
