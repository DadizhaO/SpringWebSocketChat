package springbootwebsocket.listener;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ParticipantRepository {

    private Map<String, String> activeUsers = new ConcurrentHashMap<>();

    public void add(String sessionId, String user) {
        activeUsers.put(sessionId, user);
    }

    public String getParticipant(String sessionId) {
        return activeUsers.get(sessionId);
    }

    public Map<String, String> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(Map<String, String> activeUsers) {
        this.activeUsers = activeUsers;
    }
}
