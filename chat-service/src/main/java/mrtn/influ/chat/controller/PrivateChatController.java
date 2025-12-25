package mrtn.influ.chat.controller;

import mrtn.influ.chat.dto.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Objects;

@Controller
public class PrivateChatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateChatController.class);

    private final SimpMessagingTemplate messagingTemplate;

    public PrivateChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.private")
    public void handlePrivateMessage(
            ChatMessage chatMessage, Principal principal) {
        if(Objects.nonNull(chatMessage) && Objects.nonNull(principal)) {
            LOGGER.info(chatMessage.toString());
            LOGGER.info(principal.toString());

            messagingTemplate.convertAndSendToUser(
                    chatMessage.getTo(),
                    "/queue/messages",
                    chatMessage.getMessage()
            );
        }
    }
}
