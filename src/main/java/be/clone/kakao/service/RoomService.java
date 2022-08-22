package be.clone.kakao.service;

import Controller.response.ResponseDto;
import Domain.Room;
import Domain.RoomDto.RoomRequestDto;
import Domain.RoomDto.RoomResponseDto;
import Repository.RoomRepositoy;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
public class RoomService {
    private final RoomRepositoy roomRepositoy;
    private Member member;
    private final RoomRequestDto roomRequestDto;

    @Transactional
    public ResponseDto<?> createRoom(Long memberId)
            throws IOException {

        Room room = Room.builder()
                .room_name(roomRequestDto.getRoom_name())
//                .people(Math.toIntExact(roomRequestDto.getPeople()))
                .build();

        roomRepositoy.save(room);

        return ResponseDto.success(
                RoomRequestDto.builder()

                .build()
        );
    }

    @Transactional
    public ResponseDto<?> getRoom(Long memberId)
    throws IOException {
        List<Room> roomList = roomRepositoy.findAllByOrderByModifiedAtDesc();
        return new ResponseDto.success(
                Room roomList

        );
    }

    @Transactional
    public  ResponseDto<?> putRoom(){}

    @Transactional
    public  ResponseDto<?> deleteRoom(){}
}