package be.clone.kakao.domain.roommaster;

import be.clone.kakao.domain.chat.Chat;
import be.clone.kakao.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoom {
    private Long roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(Long roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public void handlerActions(WebSocketSession session, Chat chatMessage, ChatService chatService) {
        sessions.add(session);
        sendMessage(chatMessage, chatService);
    }

    public void send(Chat chat, ChatService chatService){
        sendMessage(chat,chatService);
    }
    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }
}
