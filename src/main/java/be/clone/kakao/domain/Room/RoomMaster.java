package be.clone.kakao.domain.Room;

import be.clone.kakao.domain.Timestamped;
import be.clone.kakao.domain.member.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends Timestamped {
    @Id
    @Column(nullable = false)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String roomname;

    @Column(nullable = true)
    private int people;

//    @JoinColumn(nullable = false, Cashca = )
//    private chatlist chatlist;


}
