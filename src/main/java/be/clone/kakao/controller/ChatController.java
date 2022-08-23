package be.clone.kakao.controller;

import be.clone.kakao.domain.roommaster.RoomMaster;
import be.clone.kakao.domain.roommaster.dto.RoomRequestDto;
import be.clone.kakao.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public RoomMaster createRoom(@RequestBody RoomRequestDto requestDto) {
        return chatService.createRoom(requestDto);
    }

    @GetMapping
    public List<RoomMaster> findAllRoom() {
        return chatService.findAllRoom();
    }
}