package be.clone.kakao.repository;

import be.clone.kakao.domain.Room.RoomDetail;
import be.clone.kakao.domain.Room.RoomMaster;
import be.clone.kakao.domain.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    String COUNT_QUERY_STRING = "SELECT COUNT(*) FROM chat c " +
            "LEFT OUTER JOIN room_detail rd ON c.room_detail_id = rd.id " +
            "LEFT OUTER JOIN room_master rm ON rm.id = rd.room_master_id " +
            "WHERE " +
            "rm.id = :roomMasterId AND " +
            "c.chat_id > :chatId";

    @Query(value = COUNT_QUERY_STRING, nativeQuery = true)
    Long countFromLastReadChat(@Param(value = "roomMasterId") Long roomMasterId, @Param(value = "chatId") Long chatId);

    Long countByRoomDetail_RoomMaster_IdAndRoomDetail_Member_MemberIdAndChatIdAfter(Long roomMasterId, Long memberId, Long chatId);

    List<Chat> findByRoomDetail_RoomMaster_IdOrderByCreatedAtAsc(Long masterId);

    Optional<Chat> findFirstByRoomDetail_RoomMaster_IdOrderByCreatedAtDesc(Long roomMasterId);

}
