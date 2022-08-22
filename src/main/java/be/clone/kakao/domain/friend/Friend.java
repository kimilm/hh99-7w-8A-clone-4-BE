package be.clone.kakao.domain.friend;

import be.clone.kakao.domain.Timestamped;
import be.clone.kakao.domain.member.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.security.auth.callback.TextInputCallback;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member from;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member to;

    @Column(nullable = false)
    private String friendName;

    public Friend(Member from, Member to, String friendName) {
        this.from = from;
        this.to = to;
        this.friendName = friendName;
    }
}
