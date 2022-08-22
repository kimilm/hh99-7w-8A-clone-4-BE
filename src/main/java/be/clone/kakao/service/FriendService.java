package be.clone.kakao.service;

import be.clone.kakao.domain.friend.Friend;
import be.clone.kakao.domain.friend.dto.FriendRequestDto;
import be.clone.kakao.domain.friend.dto.FriendResponseDto;
import be.clone.kakao.domain.member.Member;
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
    public Long addFriend(FriendRequestDto friendRequestDto, Member from) {

        if (from.getEmail().equals(friendRequestDto.getEmail())) {
            throw new IllegalArgumentException("자기 자신은 친구로 추가할 수 없습니다.");
        }

        Member to = memberRepository.findByEmail(friendRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        if (friendRepository.existsByFromAndTo(from, to)) {
            throw new IllegalArgumentException("이미 친구로 추가된 사용자 입니다.");
        }

        Friend friend = new Friend(from, to, to.getNickname());

        return friendRepository.save(friend).getId();
    }

    // 친구 리스트 가져오기
    @Transactional(readOnly = true)
    public List<FriendResponseDto> getFriends(Member member) {

        List<Friend> friends = friendRepository.findByFrom(member);

        return friends.stream()
                .map(Friend::getTo)
                .map(FriendResponseDto::of)
                .collect(Collectors.toList());
    }
}
