package be.clone.kakao.repository;

import Domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepositoy extends JpaRepository<Room, Long> {
List<Room>findAllByOrderByModifiedAtDesc();
}
