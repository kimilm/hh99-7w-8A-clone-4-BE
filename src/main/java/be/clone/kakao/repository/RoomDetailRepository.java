package be.clone.kakao.repository;

import be.clone.kakao.domain.Room.RoomDetail;
import org.springframework.data.jpa.repository.JpaRepository;

//@NoRepositoryBean
public interface RoomRepository extends JpaRepository<RoomDetail, Long> {
    //List<RoomDetail>findAllByOrderByModifiedAtDesc();
//List<RoomDetail> findAllByRoom(RoomDetail roomDetail);
    }
