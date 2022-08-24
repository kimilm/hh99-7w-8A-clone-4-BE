package be.clone.kakao.controller;

import be.clone.kakao.domain.Room.dto.RoomMasterResponseDto;
import be.clone.kakao.domain.chat.dto.ChatDto;
import be.clone.kakao.service.ChatService;
import be.clone.kakao.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    private final ChatService chatService;
    private final RoomService roomService;

    //"/pub/chat/enter"
    //메세지 받았을 때
    @MessageMapping(value = "/chat/room/{roomId}")
    public void message(@DestinationVariable String roomId, ChatDto message) {
        //채팅 저장
        message = chatService.saveChat(Long.parseLong(roomId), message);
        log.info("pub success " + message.getContent());
        //채팅방에 정보 전달
        template.convertAndSend("/sub/chat/room/" + roomId, message);
    }

    // 사용자가 속한 채팅방 리스트 불러오기
    @MessageMapping(value = "/room/{memberId}")
    public void enterRoom(@DestinationVariable String memberId) {
        List<RoomMasterResponseDto> rooms = roomService.getRoom(memberId);
        log.info("room list");
        template.convertAndSend("/sub/room/" + memberId, rooms);
    }

    // append
    @MessageMapping(value = "/room/invite/{memberId}")
    public void inviteRoom(@DestinationVariable String memberId, List<Long> friends) {
        // 채팅방
        // for

        // memberId한테도 리턴할 것

        for (Long friendId : friends) {
            log.info(friendId + " 로그");
            template.convertAndSend("/sub/room/invite/" + friendId, new SimpleFriendResponseDto(friendId) + "번 회원 초대 왼료");
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class SimpleFriendRequestDto {
        private List<Long> friendId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class SimpleFriendResponseDto {
        private Long friendId;
    }
}