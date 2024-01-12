package com.committers.snowflowerthon.committersserver.common.validation;

import com.committers.snowflowerthon.committersserver.common.response.exception.*;
import com.committers.snowflowerthon.committersserver.domain.item.entity.Item;
import com.committers.snowflowerthon.committersserver.domain.item.entity.ItemRepository;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import com.committers.snowflowerthon.committersserver.domain.univ.entity.Univ;
import com.committers.snowflowerthon.committersserver.domain.univ.entity.UnivRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ValidationService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final UnivRepository univRepository;

    public Member valMember(String nickname) {
        return memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Member valMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
    }
    /*
    public List<Member> valMemberList(String nickname) {
        List<Member> memberList = memberRepository.findAllByNickname(nickname);
        if (memberList == null || memberList.isEmpty()) {
            throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return memberList;
    }*/

    public Item valItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemException(ErrorCode.ITEM_NOT_FOUND));
    }

    public Univ valUniv(Long univId) {
        return univRepository.findById(univId)
                .orElseThrow(() -> new UnivException(ErrorCode.UNIV_NOT_FOUND));
    }

    public Univ valUniv(String univName) { // 예외를 발생시키지 않음에 유의
        return univRepository.findByUnivName(univName)
                .orElse(null);
    }

}
