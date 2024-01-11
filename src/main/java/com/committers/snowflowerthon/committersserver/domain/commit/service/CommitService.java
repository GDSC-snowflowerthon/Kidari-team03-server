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
