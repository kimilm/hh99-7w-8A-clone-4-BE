package be.clone.kakao.service;

import be.clone.kakao.domain.friend.Friend;
import be.clone.kakao.domain.friend.dto.FriendRequestDto;
import be.clone.kakao.domain.friend.dto.FriendResponseDto;
import be.clone.kakao.domain.friend.dto.FriendUpdateRequestDto;
import be.clone.kakao.domain.member.Member;
import be.clone.kakao.domain.member.dto.ProfileResponseDto;
import be.clone.kakao.repository.FriendRepository;
import be.clone.kakao.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final MemberRepository memberRepository;

    private final FriendRepository friendRepository;

    // 친구 추가
    public ProfileResponseDto addFriend(FriendRequestDto friendRequestDto, Member from) {

        if (from.getEmail().equals(friendRequestDto.getEmail())) {
            throw new IllegalArgumentException("자기 자신은 친구로 추가할 수 없습니다.");
        }

        Member to = memberRepository.findByEmail(friendRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        if (friendRepository.existsByFromAndTo(from, to)) {
            throw new IllegalArgumentException("이미 친구로 추가된 사용자 입니다.");
        }

        Friend friend = new Friend(from, to);

        friendRepository.save(friend);

        return ProfileResponseDto.of(to);
    }

    // 친구 리스트 가져오기
    @Transactional(readOnly = true)
    public List<FriendResponseDto> getFriends(Member member) {

        List<Friend> friends = friendRepository.findByFrom(member);

        return friends.stream()
                .map(FriendResponseDto::of)
                .collect(Collectors.toList());
    }

    // 친구 이름 수정
    public String updateFriendName(Long friendId, FriendUpdateRequestDto friendUpdateRequestDto, Member member) {

        Friend friend = friendRepository.findByFromAndTo_MemberId(member, friendId)
                .orElseThrow(() -> new IllegalArgumentException("친구로 등록되지 않은 사용자입니다."));

        friend.updateName(friendUpdateRequestDto.getFriendName());

        return friendRepository.save(friend).getFriendName();
    }

    @Transactional
    public void deleteFriend(Member member, Long toId) {

        if (!friendRepository.existsByFromAndTo_MemberId(member, toId)) {
            throw new IllegalArgumentException("친구로 등록되지 않은 사용자입니다.");
        }

        friendRepository.deleteByFromAndTo_MemberId(member, toId);
    }
}

