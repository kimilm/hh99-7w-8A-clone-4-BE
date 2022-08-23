package be.clone.kakao.service;

import be.clone.kakao.domain.Room.RoomDetail;
import be.clone.kakao.domain.chat.Chat;
import be.clone.kakao.domain.chat.dto.ChatDto;
import be.clone.kakao.repository.ChatRepository;
import be.clone.kakao.repository.RoomDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final RoomDetailRepository roomDetailRepository;

    private final ChatRepository chatRepository;


    @Transactional
    public void saveChat(Long roomId, ChatDto message) {
        RoomDetail roomDetail = roomDetailRepository.findByRoomMaster_IdAndMember_MemberId(roomId, message.getMemberId())
                .orElseThrow(() -> new NullPointerException("해당하는 룸 세부정보가 없습니다."));

        // 최신 채팅 업데이트
        roomDetail.getRoomMaster().updateRecentChat(message.getContent());

        Chat chat = Chat.builder()
                .roomDetail(roomDetail)
                .message(message.getContent())
                .isImg(false)
                .build();
        chatRepository.save(chat);
    }

    @Transactional(readOnly = true)
    public List<ChatDto> getRoomChats(Long roomId) {
        List<Chat> chats = chatRepository.findByRoomDetail_RoomMaster_IdOrderByCreatedAtAsc(roomId);

        return chats.stream()
                .map(ChatDto::new)
                .collect(Collectors.toList());
    }
}
