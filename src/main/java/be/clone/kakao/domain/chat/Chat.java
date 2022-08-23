package be.clone.kakao.domain.chat;

import be.clone.kakao.domain.roomdetail.RoomDetail;
import be.clone.kakao.domain.Timestamped;
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
public class Chat extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @JoinColumn(name = "room_detail_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private RoomDetail roomDetail;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private Boolean isImg;

}

