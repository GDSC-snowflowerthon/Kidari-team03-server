package com.committers.snowflowerthon.committersserver.auth.github;

import com.committers.snowflowerthon.committersserver.auth.config.OAuth2MemberDto;
import com.committers.snowflowerthon.committersserver.domain.commit.entity.Commit;
import com.committers.snowflowerthon.committersserver.domain.commit.entity.CommitRepository;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GitHubService {

    private final MemberRepository memberRepository;
    private final CommitRepository commitRepository;

    // 눈송이 설정(재접속 시)
    @Transactional
    public Long resetSnowflake(Member member) {

        // 현재 커밋 수 조회
        
        // 저장된 커밋 수 조회
        
        // 현재 커밋 수 < 저장된 커밋 수
        // 줄어든 커밋 수만큼 눈송이 빼앗기
        // 눈송이 = max(눈송이, 0)
        
        // 현재 커밋 수 > 저장된 커밋 수
        // 늘어난 커밋 수만큼 눈송이 추가
        
        // 현재 커밋 수로 커밋 객체 갱신
        // 현재 눈송이 수로 멤버 객체 갱신

        return 0L;
    }

    
    // 눈송이 설정(첫 가입 시)
    @Transactional
    public Long setSnowflake(OAuth2MemberDto memberDto) {
        
        // 깃허브에서 커밋 개수 가져오기
        String nickname = memberDto.getNickname();
        Long totalCommits = Long.valueOf(getRepos(nickname));

        // 멤버 커밋 개수 업데이트
        Member member = memberRepository.findByNickname(memberDto.getNickname()).get();
        member.updateSnowflake(totalCommits);

        // 커밋 객체 생성
        Commit commit = Commit.builder()
                .count(totalCommits)
                .member(member)
                .build();

        // 커밋 객체 저장
        commitRepository.save(commit);

        return 0L;
    }

    @Transactional
    private int getRepos(String nickname) {
        RestTemplate restTemplate = new RestTemplate();

        int page = 1;
        int perPage = 100; // 페이지당 아이템 수 (GitHub API에서 제공하는 최대값은 100)
        String gitHubApi = "https://api.github.com/users/{username}/repos";

        int totalCommits = 0; // 커밋 수

        while (true) {
            // GitHub API 호출 - 특정 사용자의 레포지토리 목록을 가져옴
            ResponseEntity<List> reposResponse = restTemplate.getForEntity(
                    gitHubApi + "?page={page}&per_page={perPage}",
                    List.class, nickname, page, perPage);

            if (reposResponse.getStatusCode().is2xxSuccessful() &&reposResponse.getBody() != null) {
                List<String> repositories = reposResponse.getBody(); // 사용자 레포 전체

                if (repositories.isEmpty()) { // 레포가 더 없으면 종료
                    break;
                }

                // repositories 객체에 담긴 모든 레포에 대해 커밋 수 계산
                totalCommits += getCommits(nickname, repositories);

                // 다음 페이지로 이동
                page++;
            }
        }
        return totalCommits;
    }

    private int getCommits(String nickname, List<String> repositories) {

        RestTemplate restTemplate = new RestTemplate();
        String gitHubApi = "https://api.github.com/repos/{owner}/{repo}/stats/contributors";
        int totalCommits = 0;

        for (String repoName: repositories) {
            // GitHub API 호출 - 레포지토리의 기여자 통계 정보를 가져옴
            ResponseEntity<Object[]> contributionsResponse = restTemplate.getForEntity(gitHubApi, Object[].class, nickname, repoName);

            if (contributionsResponse.getStatusCode().is2xxSuccessful() && contributionsResponse.getBody() != null) {
                for (Object contributor : contributionsResponse.getBody()) {
                    String contributorLogin = (String) ((HashMap<?, ?>) contributor).get("author.login");
                    if (nickname.equals(contributorLogin)) {
                        totalCommits += (int) ((HashMap<?, ?>) contributor).get("total");
                    }
                }
            }
        }

        return totalCommits;
    }
}
