package be.clone.kakao.domain.member;

import be.clone.kakao.domain.Timestamped;
import be.clone.kakao.domain.member.dto.SignupRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profilePic;

    @Column(nullable = false)
    private String introduce;

    private Member(String email, String password, String nickname, String profilePic) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profilePic = profilePic;
        this.introduce = "";
    }

    public static Member of(SignupRequestDto requestDto, String encodedPassword, String profilePic) {
        return new Member(requestDto.getEmail(), encodedPassword, requestDto.getNickname(), profilePic);
    }
}
