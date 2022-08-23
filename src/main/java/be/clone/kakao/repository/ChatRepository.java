package be.clone.kakao.repository;

import be.clone.kakao.domain.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    String COUNT_QUERY_STRING = "SELECT COUNT(*) FROM chat c " +
            "LEFT OUTER JOIN room_detail rd ON c.room_detail_id = rd.id " +
            "LEFT OUTER JOIN room_master rm ON rm.id = rd.room_master_id " +
            "WHERE " +
            "rm.id = :roomMasterId AND " +
            "c.chat_id > :chatId";

    @Query(value = COUNT_QUERY_STRING, nativeQuery = true)
    Long countFromLastReadChat(@Param(value = "roomMasterId") Long roomMasterId, @Param(value = "chatId") Long chatId);

    List<Chat> findByRoomDetail_RoomMaster_IdOrderByCreatedAtAsc(Long masterId);
}
