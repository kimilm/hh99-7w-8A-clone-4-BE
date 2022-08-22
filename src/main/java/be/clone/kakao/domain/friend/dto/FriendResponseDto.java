package be.clone.kakao.domain.friend.dto;

import be.clone.kakao.domain.friend.Friend;
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

    public static FriendResponseDto of(Friend friend) {
        Member to = friend.getTo();
        String friendName = to.getNickname();

        // 내가 설정한 친구 이름이 있다면 가져오기
        if (friend.getFriendName() != null) {
            friendName = friend.getFriendName();
        }

        return new FriendResponseDto(
                to.getMemberId(),
                friendName,
                to.getProfilePic(),
                to.getIntroduce()
        );
    }
}
