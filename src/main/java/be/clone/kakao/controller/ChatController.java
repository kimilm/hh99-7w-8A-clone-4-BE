package be.clone.kakao.controller;

import be.clone.kakao.domain.chat.dto.ChatDto;
import be.clone.kakao.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/api/room/{roomId}")
    public ResponseEntity<?> getRoomChats(
            @PathVariable Long roomId
    ) {

        List<ChatDto> chats = chatService.getRoomChats(roomId);

        return ResponseEntity.ok()
                .body(chats);
    }
}
