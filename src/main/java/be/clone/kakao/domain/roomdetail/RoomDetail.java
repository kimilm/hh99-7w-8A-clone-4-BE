package be.clone.kakao.domain.roomdetail;

import be.clone.kakao.domain.roommaster.RoomMaster;
import be.clone.kakao.domain.Timestamped;
import be.clone.kakao.domain.member.Member;
import be.clone.kakao.repository.RoomMasterRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoomDetail extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomDetailId;

    @JoinColumn(name = "room_master_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private RoomMaster master;

    @JoinColumn(name = "room_member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column
    private Long chat_id;

    @Column(nullable = false)
    private Boolean alarm;

    public RoomDetail(RoomMaster master, Member member){
        this.master = master;
        this.member = member;
        this.chat_id = null;
        this.alarm = true;
    }

    public void RecentMessage(Long chatId) {
        this.chat_id = chatId;
    }
}
