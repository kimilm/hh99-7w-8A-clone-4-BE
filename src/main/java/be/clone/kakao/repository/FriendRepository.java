package be.clone.kakao.repository;

import be.clone.kakao.domain.friend.Friend;
import be.clone.kakao.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    boolean existsByFromAndTo(Member from, Member to);

    List<Friend> findByFrom(Member member);

}
