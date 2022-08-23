package be.clone.kakao.service;

import be.clone.kakao.domain.chat.Chat;
import be.clone.kakao.domain.chat.dto.ChatRequestDto;
import be.clone.kakao.domain.member.Member;
import be.clone.kakao.domain.roomdetail.RoomDetail;
import be.clone.kakao.domain.roommaster.RoomMaster;
import be.clone.kakao.domain.roommaster.dto.RoomRequestDto;
import be.clone.kakao.repository.ChatRepository;
import be.clone.kakao.repository.RoomDetailRepository;
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

    private final RoomDetailRepository roomDetailRepository;

    private final ChatRepository chatRepository;

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

    public void saveChat(Long roomId, ChatRequestDto message) {
        RoomDetail roomDetail = roomDetailRepository.findByMaster_RoomMasterIdAndMember_MemberId(roomId, message.getMemberId())
                .orElseThrow(() -> new NullPointerException("해당하는 룸 세부정보가 없습니다."));
        Chat chat = Chat.builder()
                .roomDetail(roomDetail)
                .message(message.getContent())
                .isImg(false)
                .build();
        chatRepository.save(chat);
    }
}
