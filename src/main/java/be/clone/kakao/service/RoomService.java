package be.clone.kakao.service;


import be.clone.kakao.domain.Room.RoomDetail;
import be.clone.kakao.domain.Room.RoomMaster;
import be.clone.kakao.domain.Room.dto.RoomInviteDto;
import be.clone.kakao.domain.Room.dto.RoomMasterRequestDto;
import be.clone.kakao.domain.Room.dto.RoomMasterResponseDto;
import be.clone.kakao.domain.member.Member;
import be.clone.kakao.jwt.userdetails.UserDetailsImpl;
import be.clone.kakao.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RoomService {
    private final MemberRepository memberRepository;
    private final RoomDetailRepository roomDetailRepository;
    private final RoomMasterRepository roomMasterRepository;
    private final ChatRepository chatRepository;


    private final FriendRepository friendRepository;

    @Transactional
    public Long createRoom(Member member, RoomMasterRequestDto requestDto) {
        RoomMaster roomMaster = RoomMaster.builder()
                .roomName(requestDto.getRoomName())
                .roomDetails(new ArrayList<>())
                .build();

        RoomDetail roomDetail = RoomDetail.builder()
                .member(member)
                .roomMaster(roomMaster)
                .build();

        roomMaster.getRoomDetails().add(roomDetail);

        roomMasterRepository.save(roomMaster);

        return roomMaster.getId();
    }

    @Transactional(readOnly = true)
    public List<RoomMasterResponseDto> getRoom(String memberId) {
        Member member = memberRepository.findById(Long.parseLong(memberId)).orElseThrow();
        return getRoom(member);
    }

    @Transactional(readOnly = true)
    public List<RoomMasterResponseDto> getRoom(Member member) {
        List<RoomDetail> allByOrderByModifiedAtDesc = roomDetailRepository.findAllByMemberOrderByModifiedAtDesc(member);
        List<RoomMasterResponseDto> dtoList = new ArrayList<>();

        for (RoomDetail roomDetail : allByOrderByModifiedAtDesc) {

            Long roomMasterId = roomDetail.getRoomMaster().getId();
            String recentChat = roomDetail.getRoomMaster().getRecentChat();
            String roomName = roomDetail.getRoomMaster().getRoomName();
            Long people = roomDetailRepository.countByRoomMaster_Id(roomMasterId);

            Long unReadCount = 0L;
            if (roomDetail.getChatId() != null) {
                unReadCount = chatRepository.countFromLastReadChat(roomMasterId, roomDetail.getChatId());
            }

            RoomMasterResponseDto responseDto = new RoomMasterResponseDto(roomMasterId, roomName, recentChat, people);
            dtoList.add(responseDto);
        }
        return dtoList;

    }

    @Transactional
    public RoomMaster putRoom(Member member, RoomMasterRequestDto requestDto, Long roomMasterId) {
        RoomMaster roomMaster = roomMasterRepository.findById(roomMasterId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));
        if (!roomDetailRepository.existsByMemberAndRoomMaster(member, roomMaster)) {
            throw new IllegalArgumentException("작성자만 수정 가능합니다.");
        }
        roomMaster.update(requestDto);
        return roomMaster;
    }

    public void Invite(UserDetailsImpl userDetails, Long roomMasterId, RoomInviteDto requestDto) {
        RoomMaster roomMaster = roomMasterRepository.findById(roomMasterId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));
        Member me = userDetails.getMember();
        for (Long id : requestDto.getFriends()) {
            if (friendRepository.existsByFromAndTo_MemberId(me, id)) {
                Member member = memberRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
                RoomDetail roomDetail = roomDetailRepository.findByRoomMaster_IdAndMember_MemberId(roomMasterId, id)
                        .orElse(new RoomDetail(roomMaster, member));
                roomDetailRepository.save(roomDetail);
            }
        }
    }


//    @Transactional
//    public RoomMaster deleteRoom(Member member, Long roomMasterId) {
//        RoomMaster roomMaster = roomMasterRepository.findById(roomMasterId)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));
//        if (roomDetailRepository.existsByMemberAndRoomMaster(member, roomMaster)) {
//            throw new IllegalArgumentException("작성자만 지울 수 있습니다.");
//        }
//        roomMasterRepository.deleteByRoomMaster(roomMasterId);
//        roomMasterRepository.delete(roomMaster);
//        return roomMaster;
//    }

//    @Transactional
//    public List<RoomDetailRequestDto> inviteFriends(Member member, Long roomMasterId) {
//        List<Friend> allByOrderByModifiedAtDesc = friendRepository.findByFrom(member);
//        List<RoomDetailRequestDto> dtoList = new ArrayList<>();
//        for (Friend friend : allByOrderByModifiedAtDesc) {
//            Long friends = friend.getId();
//            RoomDetailRequestDto requestDto = new RoomDetailRequestDto(friends);
//            dtoList.add(requestDto);
//        }
//        return dtoList;
//    }
}

