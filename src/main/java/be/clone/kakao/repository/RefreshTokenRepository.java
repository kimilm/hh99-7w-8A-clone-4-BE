package be.clone.kakao.repository;

import be.clone.kakao.domain.jwttoken.RefreshToken;
import be.clone.kakao.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);

    void deleteByMember(Member member);

}
