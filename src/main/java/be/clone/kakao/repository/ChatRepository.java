package be.clone.kakao.repository;

import be.clone.kakao.domain.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByRoomDetail_RoomMaster_IdOrderByCreatedAtAsc(Long masterId);
}
