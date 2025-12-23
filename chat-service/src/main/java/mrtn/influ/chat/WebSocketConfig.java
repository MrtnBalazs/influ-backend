package mrtn.influ.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Messages sent to /app/** go to @MessageMapping methods
        registry.setApplicationDestinationPrefixes("/app");

        // Simple in-memory broker
        // TODO change to redis or other message broker for horizontal scaling
        registry.enableSimpleBroker("/queue");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat")
                .addInterceptors(new UserHandshakeInterceptor())
                .setHandshakeHandler(new UserHandshakeHandler())
                .setAllowedOriginPatterns("*");
    }
}
