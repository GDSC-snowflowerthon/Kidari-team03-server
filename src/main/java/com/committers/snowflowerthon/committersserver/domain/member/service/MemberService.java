package com.committers.snowflowerthon.committersserver.domain.member.service;

import com.committers.snowflowerthon.committersserver.domain.follow.service.FollowService;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberOtherResDto;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberSearchResDto;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final FollowService followService;

    public boolean growSnowman(Long id) {
        Member member = getMemberById(id);
        if (member == null || member.useSnowflake()) { // useSnowflake()가 눈송이 소모 성공시 true 반환함
            return false;
        }
        member.growSnowmanHeight(); // snowmanHeight 필드의 값이 1 증가
        memberRepository.save(member);
        return true;
    }

    public MemberSearchResDto searchMember(String nickname) {
        Member member = getMemberByNickname(nickname);
        if (member == null){
            return null;
        }
        Boolean followStatus = followService.followStatus(member.getId());
        return MemberSearchResDto.toDto(member, followStatus);
    }

    public MemberOtherResDto getOtherMember(String nickname){
        Member member = getMemberByNickname(nickname);
        if (member == null) {
            return null;
        }
        Boolean followStatus = followService.followStatus(member.getId());
        return MemberOtherResDto.toDto(member, followStatus);
    }

    public Member getMemberById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.orElse(null);
    }

    public Member getMemberByNickname(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        return member.orElse(null);
    }
}