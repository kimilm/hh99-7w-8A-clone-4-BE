package be.clone.kakao.config.stomp;

import be.clone.kakao.repository.RoomDetailRepository;
import be.clone.kakao.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler extends ChannelInterceptorAdapter {

    private final RoomService roomService;

    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        List<String> disconnectedMemberId = accessor.getNativeHeader("memberId");
        List<String> disconnectedRoomId = accessor.getNativeHeader("roomId");
        // 채팅방 disconnect 상태 감지
        if (disconnectedMemberId != null) {
            Long memberId = Long.parseLong(disconnectedMemberId.get(0));
            Long roomId = Long.parseLong(disconnectedRoomId.get(0));

            roomService.updateLastReadChat(roomId, memberId);
        }
    }
}
