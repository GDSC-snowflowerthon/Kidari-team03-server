package com.committers.snowflowerthon.committersserver.auth.github;

import com.committers.snowflowerthon.committersserver.auth.config.OAuth2MemberDto;
import com.committers.snowflowerthon.committersserver.common.response.exception.ErrorCode;
import com.committers.snowflowerthon.committersserver.common.response.exception.MemberException;
import com.committers.snowflowerthon.committersserver.domain.commit.entity.Commit;
import com.committers.snowflowerthon.committersserver.domain.commit.entity.CommitRepository;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class GitHubService {

    @Transactional
    public int getRepos(String nickname) {
        RestTemplate restTemplate = new RestTemplate();

        int page = 1;
        int perPage = 100; // 페이지당 아이템 수 (GitHub API에서 제공하는 최대값은 100)
        String gitHubApi = "https://api.github.com/users/%s/repos?page=%d&per_page=%d";

        int totalCommits = 0; // 커밋 수

        while (true) {
            // GitHub API 호출 - 특정 사용자의 레포지토리 목록을 가져옴
            ResponseEntity<List> reposResponse = restTemplate.getForEntity(
                    String.format(gitHubApi + nickname, page, perPage),
                    List.class);

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
        String gitHubApi = "https://api.github.com/repos/%s/%s/stats/contributors";
        int totalCommits = 0;

        for (String repoName: repositories) {
            // GitHub API 호출 - 레포지토리의 기여자 통계 정보를 가져옴
            ResponseEntity<Object[]> contributionsResponse = restTemplate.getForEntity(
                    String.format(gitHubApi, nickname, repoName),
                    Object[].class);

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
