package be.clone.kakao.repository;

import be.clone.kakao.domain.Room.RoomDetail;
import be.clone.kakao.domain.Room.RoomMaster;
import be.clone.kakao.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomDetailRepository extends JpaRepository<RoomDetail, Long> {
    boolean existsByMemberAndRoomMaster(Member member, RoomMaster roomMaster);
    Long countByRoomMaster_Id(Long roomMasterId);
    List<RoomDetail> findAllByMemberOrderByModifiedAtDesc(Member member);

}