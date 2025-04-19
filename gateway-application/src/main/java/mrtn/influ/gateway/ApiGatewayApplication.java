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
                .route("test", r -> r.path("/api/v1/test/**")
                        .uri("lb://TEST-SERVICE"))
                //.route("campaign", r -> r.path("/api/v1/campaigns/**")
                //        .uri("lb://CAMPAIGN-SERVICE"))
                .route("campaign", r -> r
                        .path("/api/campaigns/**")
                        .filters(f -> f.rewritePath("/api/campaigns/(?<segment>.*)", "/api/v1/campaigns/${segment}"))
                        .uri("lb://CAMPAIGN-SERVICE"))
                .route("auth", r -> r.path("/auth/**")
                        .uri("lb://AUTH-SERVICE"))
                .build();
    }
}
