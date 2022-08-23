package be.clone.kakao.repository;


import be.clone.kakao.domain.Room.RoomDetail;
import be.clone.kakao.domain.Room.RoomMaster;
import be.clone.kakao.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface RoomDetailRepository extends JpaRepository<RoomDetail,Long> {

    List<RoomDetail> findAllByRoomMaster(RoomMaster master);

    Optional<RoomDetail> findByRoomMaster_IdAndMember_MemberId(Long masterId, Long memberId);
    Optional<RoomDetail> findByMember(Member member);
    boolean existsByMemberAndRoomMaster(Member member, RoomMaster roomMaster);
    Long countByRoomMaster_Id(Long roomMasterId);
    List<RoomDetail> findAllByMemberOrderByModifiedAtDesc(Member member);
}
