package com.committers.snowflowerthon.committersserver.domain.member.service;

import com.committers.snowflowerthon.committersserver.auth.config.AuthenticationUtils;
import com.committers.snowflowerthon.committersserver.auth.github.GitHubService;
import com.committers.snowflowerthon.committersserver.common.response.exception.CommitException;
import com.committers.snowflowerthon.committersserver.common.response.exception.ErrorCode;
import com.committers.snowflowerthon.committersserver.common.validation.ValidationService;
import com.committers.snowflowerthon.committersserver.domain.commit.entity.Commit;
import com.committers.snowflowerthon.committersserver.domain.commit.entity.CommitRepository;
import com.committers.snowflowerthon.committersserver.domain.follow.service.FollowService;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberInfoDto;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberOtherResDto;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberSearchResDto;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import com.committers.snowflowerthon.committersserver.domain.univ.entity.Univ;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.math.NumberUtils.max;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final AuthenticationUtils authenticationUtils;
    private final MemberRepository memberRepository;
    private final CommitRepository commitRepository;
    private final ValidationService validationService;
    private final FollowService followService;
    private final GitHubService gitHubService;

    public Member getAuthMember() {
        Long memberId = authenticationUtils.getMemberId();
        Member member = validationService.valMember(memberId);
        return member;
    }

    public boolean growSnowman() {
        Member member = getAuthMember();
        if (!member.useSnowflake()){ // 눈송이를 사용하는 데 실패
            return false;
        }
        member.updateSnowmanHeight(member.getSnowmanHeight() + 1); // snowmanHeight이 1 증가
        memberRepository.save(member);
        return true;
    }

    public MemberSearchResDto searchMember(String nickname) {
        Member buddy = validationService.valMember(nickname);
        Boolean followStatus = followService.followStatus(buddy);
        return MemberSearchResDto.toDto(buddy, followStatus); // 팔로우 상태와 함께 보냄
    }

    public MemberOtherResDto getOtherMemberInfo(String nickname){
        Member buddy = validationService.valMember(nickname);
        Boolean followStatus = followService.followStatus(buddy);
        return MemberOtherResDto.toDto(buddy, followStatus); // 팔로우 상태와 함께 보냄
    }

    // 단일 유저 본인 정보 조회
    public MemberInfoDto getMemberInfo() {
        Member member = getAuthMember();

        MemberInfoDto newMemberInfo = MemberInfoDto.builder()
                .nickname(member.getNickname())
                .snowflake(refreshSnowflake(member))
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

    // 눈송이 설정(재접속 시)
    @Transactional
    public Long refreshSnowflake(Member member) {

        // 갱신된 눈송이
        Long newSnowflake = 0L;
        // 눈사람 키
        Long newHeight = member.getSnowmanHeight();

        // 현재 Commit 객체에 저장되어 있는 커밋 수 조회
        Commit commit = commitRepository.findByMember(member).orElseThrow(() -> new CommitException(ErrorCode.COMMIT_NOT_FOUND));
        Long preCommits = commit.getCount();
        log.info("resetSnowflake에서 조회한 현재 커밋 수 -> {}", preCommits);

        // 저장된 커밋 수 조회
        // 깃허브에서 커밋 개수 가져오기
        String nickname = member.getNickname();
        Long nowCommits = Long.valueOf(gitHubService.getRepos(nickname));
        log.info("resetSnowflake로 넘겨진 totalCommits -> {}", nowCommits);

        // 늘어난 커밋 수
        Long incre = nowCommits - preCommits;

        // 감소한 눈사람 키
        Long decre = 0L;

        if (incre < 0) { // 현재 깃허브 커밋 수 < 저장된 커밋 수
            // 눈송이 수의 최솟값은 0
            newSnowflake = max(member.getSnowflake() + incre, 0);

            if (member.getSnowflake() < (-1 * incre)) { // 현재 눈송이 수 < 줄어든 커밋 수면
                // 눈송이를 소거하고 남은 penalty만큼 눈사람 키 감소
                decre = (-1 * incre) - member.getSnowflake();
                // 눈사람 키의 최솟값은 1
                newHeight = max(newHeight - decre, 1);
            }
        }
        else { // 현재 커밋 수 >= 저장된 커밋 수
            // 늘어난 커밋 수만큼 눈송이 추가
            newSnowflake = member.getSnowflake() + incre;
        }

        // 현재 커밋 수로 커밋 객체 갱신
        commit.updateCount(nowCommits);
        log.info("갱신된 커밋 객체의 커밋 수 -> {}", commitRepository.findById(commit.getId()));

        // 현재 눈송이 수로 멤버 객체 갱신
        member.updateSnowflake(newSnowflake);
        log.info("갱신된 멤버 객체의 눈송이 수 -> {}", memberRepository.findById(member.getId()));

        // 현재 눈사람 키로 멤버 객체 갱신
        member = refreshHeight(member, decre, newHeight);

        return newSnowflake;
    }
}