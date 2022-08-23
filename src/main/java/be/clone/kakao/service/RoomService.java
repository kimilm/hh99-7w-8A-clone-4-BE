package be.clone.kakao.service;


import be.clone.kakao.domain.Room.RoomDetail;
import be.clone.kakao.domain.Room.RoomMaster;
import be.clone.kakao.domain.Room.dto.RoomMasterRequestDto;
import be.clone.kakao.domain.Room.dto.RoomMasterResponseDto;
import be.clone.kakao.domain.member.Member;
import be.clone.kakao.repository.RoomDetailRepository;
import be.clone.kakao.repository.RoomMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomDetailRepository roomDetailRepository;
    private final RoomMasterRepository roomMasterRepository;

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
    public List<RoomMasterResponseDto> getRoom(Member member) {
        List<RoomDetail> allByOrderByModifiedAtDesc = roomDetailRepository.findAllByMemberOrderByModifiedAtDesc(member);
        List<RoomMasterResponseDto> dtoList = new ArrayList<>();

        for (RoomDetail roomDetail : allByOrderByModifiedAtDesc) {

            Long roomMasterId = roomDetail.getRoomMaster().getId();
            String roomName = roomDetail.getRoomMaster().getRoomName();
            Long people = roomDetailRepository.countByRoomMaster_Id(roomMasterId);

            RoomMasterResponseDto responseDto = new RoomMasterResponseDto(roomMasterId, roomName, people);
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
}

//    @Transactional
//    public
