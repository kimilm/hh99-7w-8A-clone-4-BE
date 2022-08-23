package be.clone.kakao.controller;

import be.clone.kakao.domain.Room.dto.RoomMasterRequestDto;
import be.clone.kakao.domain.Room.dto.RoomMasterResponseDto;
import be.clone.kakao.domain.member.Member;
import be.clone.kakao.jwt.userdetails.UserDetailsImpl;

import be.clone.kakao.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @RequestMapping(value = "/api/rooms", method = RequestMethod.POST)
    public ResponseEntity<?> createRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @RequestBody RoomMasterRequestDto requestDto) {
        Member member = userDetails.getMember();
        Long roomMasterId = roomService.createRoom(member, requestDto);
        return ResponseEntity.ok()
                .body(roomMasterId);
    }


    @RequestMapping(value = "/api/rooms", method = RequestMethod.GET)
    public ResponseEntity<?> getRoom(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        List<RoomMasterResponseDto> roomMasterResponseDtoList = roomService.getRoom(member);
        return ResponseEntity.ok()
                .body(roomMasterResponseDtoList);
    }

    @RequestMapping(value = "/api/rooms/{roomMasterId}", method = RequestMethod.PUT)
    public ResponseEntity<?> putRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @RequestBody RoomMasterRequestDto requestDto, @PathVariable Long roomMasterId) {
        Member member = userDetails.getMember();
        roomService.putRoom(member, requestDto, roomMasterId);
        return ResponseEntity.ok()
                .body(roomMasterId);
    }

//    @RequestMapping(value = "api/room/{roomMaserId}/mamber/{mamberId}", method = RequestMethod.POST)
//    public ResponseEntity<?> postFriends(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                                         @PathVariable Long mamberId, @RequestBody RoomMasterRequestDto requestDto) {
//        Member member = userDetails.getMember();
//        roomService.postFriends(mamberId, member, requestDto);
//        return ResponseEntity.ok()
//                .body();
//
//    }

}