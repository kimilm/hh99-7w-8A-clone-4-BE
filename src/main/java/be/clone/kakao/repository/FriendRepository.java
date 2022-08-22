package be.clone.kakao.repository;

import be.clone.kakao.domain.friend.Friend;
import be.clone.kakao.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    boolean existsByFromAndTo(Member from, Member to);

    boolean existsByFromAndTo_MemberId(Member from, Long toId);

    Optional<Friend> findByFromAndTo_MemberId(Member from, Long toId);

    List<Friend> findByFrom(Member member);

    void deleteByFromAndTo_MemberId(Member member, Long toId);
}
