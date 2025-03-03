package mrtn.influ.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    @Bean
    public WebClient webClientBean() {
        return WebClient.create("http://localhost:8082/auth/verify");
    }
}
