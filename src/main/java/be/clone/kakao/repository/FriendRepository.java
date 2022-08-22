package be.clone.kakao.repository;

import be.clone.kakao.domain.friend.Friend;
import be.clone.kakao.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    boolean existsByFromAndTo(Member from, Member to);
}
