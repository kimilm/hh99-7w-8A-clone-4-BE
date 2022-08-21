package be.clone.kakao.domain.jwttoken;

import be.clone.kakao.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String tokenValue;

    public RefreshToken(Member member) {
        this.member = member;
    }

    public void updateTokenValue(String refreshToken) {
        this.tokenValue = refreshToken;
    }
}
