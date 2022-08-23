package be.clone.kakao.config;

import be.clone.kakao.domain.chat.Chat;
import be.clone.kakao.domain.chat.dto.ChatRequestDto;
import be.clone.kakao.domain.member.Member;
import be.clone.kakao.domain.roomdetail.RoomDetail;
import be.clone.kakao.domain.roommaster.ChatRoom;
import be.clone.kakao.domain.roommaster.RoomMaster;
import be.clone.kakao.jwt.userdetails.UserDetailsImpl;
import be.clone.kakao.repository.ChatRepository;
import be.clone.kakao.repository.MemberRepository;
import be.clone.kakao.repository.RoomDetailRepository;
import be.clone.kakao.repository.RoomMasterRepository;
import be.clone.kakao.service.ChatService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import java.util.HashMap;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;

    private final RoomMasterRepository roomMasterRepository;

    private final RoomDetailRepository roomDetailRepository;

    private final ChatRepository chatRepository;

    HashMap<String, WebSocketSession> sessions = new HashMap<>(); //웹소켓 세션을 담아둘 맵

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("{}", payload);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(payload);
        System.out.println(jsonNode);
        Long roomId = jsonNode.get("roomId").asLong();
        String content = jsonNode.get("content").toString();
        Long sender = jsonNode.get("sender").asLong();

        System.out.println(roomId);


        RoomMaster chatRoom = roomMasterRepository.findById(roomId).orElseThrow( 
                () -> new NullPointerException("해당 방이 존재하지 않습니다."));

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        UserDetailsImpl userDetails = (UserDetailsImpl)principal;

        Member member = memberRepository.findById(sender).orElseThrow(
                () -> new NullPointerException("해당 멤버가 존재하지 않습니다."));
        RoomDetail roomDetail = roomDetailRepository.findByMember(member).orElseGet(
                () -> roomDetailRepository.save(new RoomDetail(chatRoom,member)));

        Chat chat = Chat.builder()
                .message(content)
                .isImg(false)
                .roomDetail(roomDetail)
                .build();
        chatRepository.save(chat);

        roomDetail.RecentMessage(chat.getChatId());
        roomDetailRepository.save(roomDetail);

        for(String key : sessions.keySet()) {
            WebSocketSession wss = sessions.get(key);
            try {
                wss.sendMessage(new TextMessage(chat.getMessage()));
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //소켓 연결
        super.afterConnectionEstablished(session);
        sessions.put(session.getId(), session);

        Object principal = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(principal);
        UserDetailsImpl userDetails = (UserDetailsImpl)principal;

        session.sendMessage(new TextMessage(userDetails.getUsername() + "님이 접속했습니다."));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //소켓 종료
        sessions.remove(session.getId());
        super.afterConnectionClosed(session, status);
    }
}
