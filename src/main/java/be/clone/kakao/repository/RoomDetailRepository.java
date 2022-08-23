package be.clone.kakao.repository;


import be.clone.kakao.domain.member.Member;
import be.clone.kakao.domain.roomdetail.RoomDetail;
import be.clone.kakao.domain.roommaster.RoomMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomDetailRepository extends JpaRepository<RoomDetail,Long> {

    List<RoomDetail> findAllByMaster(RoomMaster master);

    Optional<RoomDetail> findByMaster_RoomMasterIdAndMember_MemberId(Long masterId, Long memberId);
    Optional<RoomDetail> findByMember(Member member);
    boolean existsByMemberAndRoomMaster(Member member, RoomMaster roomMaster);
    Long countByRoomMaster_Id(Long roomMasterId);
    List<RoomDetail> findAllByMemberOrderByModifiedAtDesc(Member member);
}
