package be.clone.kakao.service;

import be.clone.kakao.domain.Room.RoomDetail;
import be.clone.kakao.domain.chat.Chat;
import be.clone.kakao.domain.chat.dto.ChatRequestDto;
import be.clone.kakao.repository.ChatRepository;
import be.clone.kakao.repository.RoomDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final RoomDetailRepository roomDetailRepository;

    private final ChatRepository chatRepository;



    public void saveChat(Long roomId, ChatRequestDto message) {
        RoomDetail roomDetail = roomDetailRepository.findByRoomMaster_IdAndMember_MemberId(roomId, message.getMemberId())
                .orElseThrow(() -> new NullPointerException("해당하는 룸 세부정보가 없습니다."));
        Chat chat = Chat.builder()
                .roomDetail(roomDetail)
                .message(message.getContent())
                .isImg(false)
                .build();
        chatRepository.save(chat);
    }
}
