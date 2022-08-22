package be.clone.kakao.domain.friend.dto;

import be.clone.kakao.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendResponseDto {
    private Long memberId;
    private String nickname;
    private String profilePic;
    private String introduce;

    private FriendResponseDto(Long memberId, String nickname, String profilePic, String introduce) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profilePic = profilePic;
        this.introduce = introduce;
    }

    public static FriendResponseDto of(Member member) {
        return new FriendResponseDto(
                member.getMemberId(),
                member.getNickname(),
                member.getProfilePic(),
                member.getIntroduce()
        );
    }
}
