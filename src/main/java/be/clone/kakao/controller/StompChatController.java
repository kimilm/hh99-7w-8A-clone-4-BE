package be.clone.kakao.controller;

import be.clone.kakao.domain.chat.dto.ChatRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/publish/room/{id}")
    public void enter(@RequestParam Long id, ChatRequestDto message){
        message.setContent("환영합니다.");
        template.convertAndSend("/sub/chat/room/" + id,message);
    }

    @MessageMapping(value = "/publish/room/{id}/message")
    public void message(@RequestParam Long id, ChatRequestDto message){
        template.convertAndSend("/sub/chat/room/" + id, message);
    }
}