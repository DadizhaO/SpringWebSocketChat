package springbootwebsocket.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import springbootwebsocket.listener.ParticipantRepository;
import springbootwebsocket.model.Message;
import springbootwebsocket.model.OutputMessage;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;

@Controller
public class ChatMessageController {

    private static final Logger log = LogManager.getLogger(ChatMessageController.class.getName());

    @Autowired
    private SimpMessagingTemplate simpMessage;

    @Autowired
    private ParticipantRepository participant;

    @SubscribeMapping("/chat.participants")
    public Collection<String> getAvailableUsers() {
        return participant.getActiveUsers().values();
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/publicChat")
    public OutputMessage sendMessage(Message message) {

        log.info("In sendMessage");
        final String time = String.valueOf(LocalDateTime.now());
        return new OutputMessage(message.getSender(), message.getContent(), time);
    }

/*    @MessageMapping("/chat.addUser")
    @SendTo("topic/publicChat")
    public Message addUser(Message message, SimpMessageHeaderAccessor accessor) {
        Objects.requireNonNull(accessor.getSessionAttributes()).put("username", message.getSender());
        return message;
    }*/

    @MessageMapping("/private.{username)")
    public void sendToUser(Message message, Principal principal, @DestinationVariable("username") String username) {
        System.out.println(principal.getName());
        OutputMessage outMess = new OutputMessage(message.getSender(), message.getContent(), String.valueOf(LocalDateTime.now()));
        simpMessage.convertAndSendToUser(username, "/queue/" + username + "/direct", outMess);

    }

}
