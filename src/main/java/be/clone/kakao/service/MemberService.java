package be.clone.kakao.service;

import be.clone.kakao.domain.member.Member;
import be.clone.kakao.domain.member.dto.SignupRequestDto;
import be.clone.kakao.repository.MemberRepository;
import be.clone.kakao.util.MemberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입
    public Long signup(SignupRequestDto requestDto) {
        // Todo 패스워드 인코딩 수행
        String encodedPassword = requestDto.getPassword();
        // 기본 이미지
        String defaultProfilePic = MemberUtils.getPic();
        // 맴버 객체 생성
        Member member = Member.of(requestDto, encodedPassword, defaultProfilePic);
        // 저장 후 아이디 리턴
        return memberRepository.save(member).getMemberId();
    }
}
