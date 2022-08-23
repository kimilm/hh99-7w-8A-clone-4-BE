package be.clone.kakao.controller;

import be.clone.kakao.domain.SimpleMessageDto;
import be.clone.kakao.domain.friend.dto.FriendRequestDto;
import be.clone.kakao.domain.friend.dto.FriendResponseDto;
import be.clone.kakao.domain.friend.dto.FriendUpdateRequestDto;
import be.clone.kakao.domain.member.Member;
import be.clone.kakao.domain.member.dto.ProfileResponseDto;
import be.clone.kakao.jwt.userdetails.UserDetailsImpl;
import be.clone.kakao.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    // 친구 추가
    @PostMapping("/api/friends")
    public ResponseEntity<?> addFriend(
            @RequestBody FriendRequestDto friendRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Member member = userDetails.getMember();

        ProfileResponseDto responseDto = friendService.addFriend(friendRequestDto, member);

        return ResponseEntity.ok()
                .body(responseDto);
    }

    // 내 친구 리스트
    // TODO QueryDsl, EntityGraph
    @GetMapping("/api/friends")
    public ResponseEntity<?> getFriends(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Member member = userDetails.getMember();

        List<FriendResponseDto> friendResponseDtos = friendService.getFriends(member);

        return ResponseEntity.ok()
                .body(friendResponseDtos);
    }

    // 친구 이름 수정
    @PutMapping("/api/friends/{memberId}")
    public ResponseEntity<?> updateFriendName(
            @PathVariable Long memberId,
            @RequestBody FriendUpdateRequestDto friendUpdateRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Member member = userDetails.getMember();

        String name = friendService.updateFriendName(memberId, friendUpdateRequestDto, member);

        return ResponseEntity.ok()
                .body(new SimpleMessageDto("친구 이름 수정 완료"));
    }

    // 친구 삭제
    @DeleteMapping("/api/friends/{memberId}")
    public ResponseEntity<?> deleteFriend(
            @PathVariable Long memberId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Member member = userDetails.getMember();

        friendService.deleteFriend(member, memberId);

        return ResponseEntity.ok()
                .body(new SimpleMessageDto("친구 삭제 완료"));
    }
}
