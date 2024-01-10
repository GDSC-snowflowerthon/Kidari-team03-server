package com.committers.snowflowerthon.committersserver.domain.commit.service;

import com.committers.snowflowerthon.committersserver.auth.config.OAuth2MemberDto;
import com.committers.snowflowerthon.committersserver.auth.github.GitHubService;
import com.committers.snowflowerthon.committersserver.common.response.exception.CommitException;
import com.committers.snowflowerthon.committersserver.common.response.exception.ErrorCode;
import com.committers.snowflowerthon.committersserver.common.response.exception.MemberException;
import com.committers.snowflowerthon.committersserver.domain.commit.entity.Commit;
import com.committers.snowflowerthon.committersserver.domain.commit.entity.CommitRepository;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.math.NumberUtils.max;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommitService {

    private final MemberRepository memberRepository;
    private final CommitRepository commitRepository;
    private final GitHubService gitHubService;
    private final MemberService memberService;

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
        member = memberService.refreshHeight(member, decre, newHeight);

        return newSnowflake;
    }


    // 눈송이 설정(첫 가입 시)
    @Transactional
    public Long setSnowflake(OAuth2MemberDto memberDto) {

        log.info("첫 가입 시 눈송이 설정: setSnowflake");
        log.info("memberDto -> {}", memberDto.getNickname());

        // 깃허브에서 커밋 개수 가져오기
        String nickname = memberDto.getNickname();
        Long totalCommits = Long.valueOf(gitHubService.getRepos(nickname));
        log.info("setSnowflake로 넘겨진 totalCommits -> {}", totalCommits);

        // 멤버 커밋 개수 업데이트
        Member member = memberRepository.findByNickname(memberDto.getNickname()).orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
        log.info("멤버 기존 커밋 개수 snowflake -> {}", member.getSnowflake());
        member.updateSnowflake(totalCommits);
        log.info("멤버 커밋 개수 업데이트 snowflake -> {}", member.getSnowflake());

        // 커밋 객체 생성
        Commit commit = Commit.builder()
                .count(totalCommits)
                .member(member)
                .build();

        // 커밋 객체 저장
        commitRepository.save(commit);
        log.info("커밋 객체 -> {}", commit.getCount());
        log.info("커밋 저장 되었는지 조회: commitRepository.findById -> {}", commitRepository.findById(commit.getId()));

        return totalCommits;
    }
}
