package be.clone.kakao.domain.Room;

import be.clone.kakao.domain.Timestamped;
import be.clone.kakao.domain.member.Member;
import lombok.*;


import javax.persistence.*;
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetail extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_master_id", nullable = false)
    private RoomMaster roomMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
