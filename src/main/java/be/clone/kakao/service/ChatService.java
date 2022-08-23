package be.clone.kakao.service;

import be.clone.kakao.domain.roomdetail.RoomDetail;
import be.clone.kakao.domain.roommaster.RoomMaster;
import be.clone.kakao.domain.roommaster.dto.RoomRequestDto;
import be.clone.kakao.repository.RoomMasterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<Long, RoomMaster> chatRooms;

    private final RoomMasterRepository roomMasterRepository;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<RoomMaster> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public RoomMaster findRoomById(Long roomId) {
        return chatRooms.get(roomId);
    }

    public RoomMaster createRoom(RoomRequestDto requestDto) {
        RoomMaster roomMaster = RoomMaster.builder()
                .roomName(requestDto.getRoomName())
                .build();
        chatRooms.put(roomMaster.getRoomMasterId(), roomMaster);
        roomMasterRepository.save(roomMaster);
        return roomMaster;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
