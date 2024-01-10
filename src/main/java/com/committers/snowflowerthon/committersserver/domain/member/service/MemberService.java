package com.committers.snowflowerthon.committersserver.domain.member.service;

import com.committers.snowflowerthon.committersserver.auth.config.AuthenticationUtils;
import com.committers.snowflowerthon.committersserver.common.validation.ValidationService;
import com.committers.snowflowerthon.committersserver.domain.commit.service.CommitService;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberInfoDto;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberOtherResDto;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberSearchResDto;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import com.committers.snowflowerthon.committersserver.domain.univ.entity.Univ;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final AuthenticationUtils authenticationUtils;
    private final MemberRepository memberRepository;
    private final ValidationService validationService;
    private final CommitService commitService;
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

    // 단일 유저 본인 정보 조회
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
                .newAlarm(member.getNewAlarm())
                .build();

        return newMemberInfo;
    }

    // 단일 멤버의 눈사람 키 갱신
    public Member refreshHeight(Member member, Long decre, Long newHeight) {

        log.info("MemberService.refreshHeight");
        log.info("decre -> {}", decre);
        log.info("newHeight -> {}", newHeight);

        // 멤버의 눈사람 키 갱신
        member.updateSnowmanHeight(newHeight);
        log.info("갱신된 멤버의 snowHeight -> {}", member.getSnowmanHeight());

        // Univ의 totalHeight 갱신
        Univ univ = validationService.valUniv(member.getUniv().getId());
        log.info("대학명: " + univ.getUnivName());
        log.info("기존 totalHeight -> {}", univ.getTotalHeight());

        univ.updateTotalHeight(decre);
        log.info("갱신된 totalHeight -> {}", univ.getTotalHeight());

        return member;
    }
}