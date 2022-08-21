package be.clone.kakao.domain.member.dto;

import be.clone.kakao.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileResponseDto {
    private Long memberId;
    private String nickname;
    private String profilePic;
    private String introduce;

    private ProfileResponseDto(Long memberId, String nickname, String profilePic, String introduce) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profilePic = profilePic;
        this.introduce = introduce;
    }

    public static ProfileResponseDto of(Member member) {
        return new ProfileResponseDto(member.getMemberId(), member.getNickname(), member.getProfilePic(), member.getIntroduce());
    }
}
