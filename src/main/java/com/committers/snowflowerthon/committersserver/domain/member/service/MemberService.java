package com.committers.snowflowerthon.committersserver.domain.member.service;

import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public boolean growSnowman(Long id) {
        Member member = getMemberById(id);
        if (member != null) {
            member.growSnowmanHeight(); // snowmanHeight 필드의 값이 1 증가
            memberRepository.save(member);
            return true;
        }
        return false;
    }

    public Member getMemberById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.orElse(null);
    }
    // 단일 유저 정보 조회 시 사용
    public Member getMemberByNickname(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        return member.orElse(null);
    }
}