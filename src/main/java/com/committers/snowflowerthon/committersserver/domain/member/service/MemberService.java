package com.committers.snowflowerthon.committersserver.domain.member.service;

import com.committers.snowflowerthon.committersserver.auth.config.AuthenticationUtils;
import com.committers.snowflowerthon.committersserver.common.validation.ValidationService;
import com.committers.snowflowerthon.committersserver.domain.commit.service.CommitService;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberInfoDto;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import com.committers.snowflowerthon.committersserver.domain.univ.entity.Univ;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final AuthenticationUtils authenticationUtils;
    private final MemberRepository memberRepository;
    private final ValidationService validationService;
    private final CommitService commitService;

    public MemberInfoDto getMemberInfo() {

        Long memberId = authenticationUtils.getMemberId();
        Member member = validationService.valMember(memberId);

        MemberInfoDto newMemberInfo = MemberInfoDto.builder()
                .nickname(member.getNickname())
                .snowflake(commitService.refreshSnowflake(member))
                .snowmanHeight(member.getSnowmanHeight())
                .snowId(member.getItem().getSnowId())
                .hatId(member.getItem().getHatId())
                .decoId(member.getItem().getDecoId())
                .newAlarm()
                .build();

        return newMemberInfo;
    }

    // 단일 멤버의 눈사람 키 갱신
    public Member refreshHeight(Member member, Long decre, Long newHeight) {

        // 멤버의 눈사람 키 갱신
        member.updateSnowmanHeight(newHeight);
        log.info("newHeight -> {}", member.getSnowmanHeight());

        // Univ의 totalHeight 갱신
        Univ univ = validationService.valUniv(member.getUniv().getId());
        univ.updateTotalHeight(decre);
    }
}
