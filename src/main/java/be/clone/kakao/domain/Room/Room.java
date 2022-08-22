package be.clone.kakao.domain.Room;

import be.clone.kakao.domain.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.lang.reflect.Member;

@Builder
@AllArgsConstructor
public class Room extends Timestamped {
    @Id
    @Column(nullable = false)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String room_name;

    @Column(nullable = true)
    private int people;

    @Column
    private String msg;

//    @JoinColumn(nullable = false, Cashca = )
//    private chatlist chatlist;


}
