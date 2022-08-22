package be.clone.kakao.domain.friend;

import be.clone.kakao.domain.Timestamped;
import be.clone.kakao.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Member from;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Member to;

    @Column
    private String friendName;

    public Friend(Member from, Member to) {
        this.from = from;
        this.to = to;
    }
}
