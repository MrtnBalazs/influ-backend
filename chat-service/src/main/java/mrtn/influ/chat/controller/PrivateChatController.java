package mrtn.influ.chat.controller;

import mrtn.influ.chat.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class PrivateChatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateChatController.class);

    private final SimpMessagingTemplate messagingTemplate;

    public PrivateChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.private")
    public void handlePrivateMessage(
            Message message, Principal principal) {
        LOGGER.info(message.toString());
        //LOGGER.info(principal.toString());

        // Trusted sender identity
        //String sender = principal.getName();
        message.setFrom(message.getFrom());

        // Send to target user
        messagingTemplate.convertAndSendToUser(
                message.getTo(),          // recipient userId
                "/queue/messages",        // destination
                message
        );

        // Send to target user
        messagingTemplate.convertAndSend(
                "/queue/messages",        // destination
                message
        );
    }
}
