package be.clone.kakao.domain.chat.dto;

import be.clone.kakao.domain.chat.Chat;
import be.clone.kakao.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private Long memberId;
    private String nickname;
    private String profilePic;
    private String content;
    private LocalDateTime createdAt;

    public ChatDto(Chat chat) {
        Member member = chat.getRoomDetail().getMember();
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.profilePic = member.getProfilePic();
        this.content = chat.getMessage();
        this.createdAt = chat.getCreatedAt();
    }

}
