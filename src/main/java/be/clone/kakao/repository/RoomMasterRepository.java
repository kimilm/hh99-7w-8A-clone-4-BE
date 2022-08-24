package be.clone.kakao.repository;

import be.clone.kakao.domain.Room.RoomMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomMasterRepository extends JpaRepository<RoomMaster, Long> {
//    Long deleteByRoomMaster(Long id);
}
