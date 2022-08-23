package be.clone.kakao.controller;

import be.clone.kakao.domain.chat.dto.ChatListRequestDto;
import be.clone.kakao.domain.chat.dto.ChatRequestDto;
import be.clone.kakao.domain.member.Member;
import be.clone.kakao.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    private final ChatService chatService;

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    //메세지 받았을 때
    @MessageMapping(value = "/room/{roomId}")
    public void message(@DestinationVariable String roomId, ChatRequestDto message){
        //채팅 저장
        chatService.saveChat(Long.parseLong(roomId),message);
        //채팅방에 정보 전달
        template.convertAndSend("/sub/chat/room/" + roomId, message);
        //채팅방 리스트에 정보 전달
        ChatListRequestDto listDto = new ChatListRequestDto(Long.parseLong(roomId),message);
        template.convertAndSend("/sub/room/" + roomId, listDto);
    }

    //채팅방리스트 내 체팅방 종류 조정
    @MessageMapping(value = "/room/member/{memberId}")
    public void NewRoom(@DestinationVariable String memberId, ChatRequestDto message){
        template.convertAndSend("/sub/room/member/" + memberId, message);
    }


}