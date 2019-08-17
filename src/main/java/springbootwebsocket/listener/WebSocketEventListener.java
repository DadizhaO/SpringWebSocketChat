
package springbootwebsocket.listener;

import org.apache.catalina.connector.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import springbootwebsocket.model.Message;

import java.security.Principal;

@Component
public class WebSocketEventListener {

    private static final Logger log = LogManager.getLogger(WebSocketEventListener.class.getName());

    @Autowired
    private ParticipantRepository participant;

    @Autowired
    private SimpMessageSendingOperations simpMessage;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {

        log.info(event.getMessage() + "!!!!! event mess");

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        log.info(sessionId);

        MessageHeaders headers = event.getMessage().getHeaders();
        log.info(event.getUser().getName());
        //Principal user = SimpMessageHeaderAccessor.getUser(headers);

        String user = SimpMessageHeaderAccessor.USER_HEADER;
        log.info(SimpMessageHeaderAccessor.getUser(headers));
        log.info(user);
        if (user == null) {
            return;
        }

        String session1 = SimpMessageHeaderAccessor.getSessionId(headers);
        log.info(session1);
        log.info(sessionId.equals(session1));

        participant.add(sessionId, user);
        simpMessage.convertAndSend("/topic/chat.login", participant);
        log.info("connect end");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        log.info("Disconnect in ");
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null) {

            Message message = new Message();
            message.setSender(username);
            simpMessage.convertAndSend("/topic/publicChat", message);
        }
        log.info("Disconnect end");
    }
}

