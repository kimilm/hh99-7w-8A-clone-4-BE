package be.clone.kakao.controller;

import be.clone.kakao.domain.Room.dto.RoomInviteDto;
import be.clone.kakao.domain.Room.RoomMaster;
import be.clone.kakao.domain.Room.dto.RoomDetailRequestDto;
import be.clone.kakao.domain.Room.dto.RoomMasterRequestDto;
import be.clone.kakao.domain.Room.dto.RoomMasterResponseDto;
import be.clone.kakao.domain.friend.Friend;
import be.clone.kakao.domain.member.Member;
import be.clone.kakao.jwt.userdetails.UserDetailsImpl;

import be.clone.kakao.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

//    @PostMapping("/api/rooms/{roomMasterId}")
//    public ResponseEntity<?> updateRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                                        @RequestBody RoomInviteDto requestDto,
//                                        @PathVariable Long roomMasterId){
//        roomService.Invite(userDetails,roomMasterId,requestDto);
//        return ResponseEntity.ok()
//                .body("초대 성공");
//    }

//    @RequestMapping(value = "api/room/{roomMaserId}/mamber/{mamberId}", method = RequestMethod.POST)
//    public ResponseEntity<?> postFriends(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                                         @PathVariable Long mamberId, @RequestBody RoomMasterRequestDto requestDto) {
//        Member member = userDetails.getMember();
//        roomService.postFriends(mamberId, member, requestDto);
//        return ResponseEntity.ok()
//                .body();
//
//    }


    @RequestMapping(value = "api/room/{roomMaserId}", method = RequestMethod.POST)
    public ResponseEntity<?> inviteFriends(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable RoomMaster roomMasterId) {
        Member member = userDetails.getMember();
        List<RoomDetailRequestDto> roomDetailRequestDtoList = roomService.inviteFriends(member, roomMasterId);
        return ResponseEntity.ok()
                .body(roomDetailRequestDtoList);
    }

}