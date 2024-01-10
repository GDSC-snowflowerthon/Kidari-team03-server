package com.committers.snowflowerthon.committersserver.common.validation;

import com.committers.snowflowerthon.committersserver.common.response.exception.*;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import com.committers.snowflowerthon.committersserver.domain.univ.entity.Univ;
import com.committers.snowflowerthon.committersserver.domain.univ.entity.UnivRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ValidationService {

    private final MemberRepository memberRepository;
    private final UnivRepository univRepository;

    public Member valMember(String nickname) {
        return memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Member valMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    }
    // 랭킹에서 리스트 반환 위해
    public List<Member> valMemberList(String nickname) {
        List<Member> memberList = memberRepository.findAllByNickname(nickname);
        if (memberList == null || memberList.isEmpty()) {
            throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return memberList;
    }

    public Univ valUniv(Long univId) {
        return univRepository.findById(univId)
                .orElseThrow(() -> new UnivException(ErrorCode.UNIV_NOT_FOUND));
    }
}
