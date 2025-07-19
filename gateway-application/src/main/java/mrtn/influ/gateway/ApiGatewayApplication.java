package mrtn.influ.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("test", r -> r
                        .path("/api/test/**")
                        .filters(f -> f.rewritePath("/api/test/(?<segment>.*)", "/api/v1/test/${segment}"))
                        .uri("lb://TEST-SERVICE"))
                .route("campaign", r -> r
                        .path("/api/campaigns/**")
                        .filters(f -> f.rewritePath("/api/campaigns/(?<segment>.*)", "/api/v1/campaigns/${segment}"))
                        .uri("lb://CAMPAIGN-SERVICE"))
                .route("user", r -> r
                        .path("/api/users/**")
                        .filters(f -> f.rewritePath("/api/users/(?<segment>.*)", "/api/v1/users/${segment}"))
                        .uri("lb://USER-SERVICE"))
                .route("auth", r -> r
                        .path("/api/auth/**")
                        .filters(f -> f.rewritePath("/api/auth/(?<segment>.*)", "/api/v1/auth/${segment}"))
                        .uri("lb://AUTH-SERVICE"))
                .build();
    }
}
