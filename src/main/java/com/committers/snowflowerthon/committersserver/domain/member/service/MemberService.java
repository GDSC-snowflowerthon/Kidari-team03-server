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

    public Member getMemberById(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        if (memberOptional.isPresent()) {
            return memberOptional.get();
        } else {
            throw new RuntimeException("Member not found with id: " + id); //추후 따로 Exception
        }
    }
    // 단일 유저 정보 조회 시 사용
    public Member getMemberByNickname(String nickname) {
        Optional<Member> memberOptional = memberRepository.findByNickname(nickname);
        if (memberOptional.isPresent()) {
            return memberOptional.get();
        } else {
            throw new RuntimeException("Member not found with nickname: " + nickname); //추후 따로 Exception
        }
    }
}