package com.committers.snowflowerthon.committersserver.auth.github;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class GitHubService {

    private final GitHubClient gitHubClient;
    @Value("${github.token}") String token;

    @Transactional
    public int getRepos(String nickname) {
        RestTemplate restTemplate = new RestTemplate();

        int page = 1;
        int perPage = 100; // 페이지당 아이템 수 (GitHub API에서 제공하는 최대값은 100)
//        String gitHubApi = "https://api.github.com/users/%s/repos?page=%d&per_page=%d";

        int totalCommits = 0; // 커밋 수

        while (true) {
            // GitHub API 호출 - 특정 사용자의 레포지토리 목록을 가져옴
//            ResponseEntity<List> reposResponse = restTemplate.getForEntity(
//                    String.format(gitHubApi, nickname, page, perPage),
//                    List.class);

            List<RepoResponseDto> repos = gitHubClient.getRepo(nickname, page, perPage, token);

            for (RepoResponseDto reponame : repos) {
                log.info("특정 사용자의 레포지토리 이름-> {}", reponame.full_name);
            }
            log.info("(조회 완료) 특정 사용자의 레포지토리 목록 -> {}", repos);

            if (repos.isEmpty()) { // 레포가 더 없으면 종료
                log.info("레포를 모두 조회했습니다.");
                break;
            }

            // repositories 객체에 담긴 모든 레포에 대해 커밋 수 계산
            totalCommits += getCommits(nickname, repos);

            // 다음 페이지로 이동
            page++;
            log.info("다음 페이지로 이동합니다.");
            log.info("다음 페이지: " + page);

//            if (reposResponse.getStatusCode().is2xxSuccessful() &&reposResponse.getBody() != null) {
//                List<String> repositories = reposResponse.getBody(); // 사용자 레포 전체
//                for (String repo : repositories) {
//                    log.info("repo name -> {}", repo);
//                }
//
//                if (repositories.isEmpty()) { // 레포가 더 없으면 종료
//                    break;
//                }
//
//                // repositories 객체에 담긴 모든 레포에 대해 커밋 수 계산
//                totalCommits += getCommits(nickname, repositories);
//
//                // 다음 페이지로 이동
//                page++;
//            }
        }
        return totalCommits;
    }

    private int getCommits(String nickname, List<RepoResponseDto> repositories) {

//        RestTemplate restTemplate = new RestTemplate();
//        String gitHubApi = "https://api.github.com/repos/%s/%s/stats/contributors";

        int totalCommits = 0;

        for (RepoResponseDto repo: repositories) {

//            ResponseEntity<Object[]> contributionsResponse = restTemplate.getForEntity(
//                    String.format(gitHubApi, nickname, repoName),
//                    Object[].class);
//            ResponseEntity<List> contributionsResponse = restTemplate.getForEntity(
//                    String.format(gitHubApi, nickname, repoName),
//                    List.class);

            // GitHub API 호출 - 레포지토리의 기여자 통계 정보를 가져옴
            List<CommitResponseDto> commits = gitHubClient.getCommit(repo.full_name, token);

            for(CommitResponseDto com : commits) {
                String committer = com.author.login;
                if(committer.isEmpty() || committer.isBlank()) {
                    log.info("커미터 부재");
                    continue;
                }
                log.info("커밋 sha -> {}", com.sha);
                log.info("커미터 -> {}", com.author.login);
                if (committer.equals(nickname)) {
                    totalCommits++;
                }
            }

//            if (contributionsResponse.getStatusCode().is2xxSuccessful() && contributionsResponse.getBody() != null) {
//                for (Object contributor : contributionsResponse.getBody()) {
//                    String contributorLogin = (String) ((HashMap<?, ?>) contributor).get("author.login");
//                    if (nickname.equals(contributorLogin)) {
//                        totalCommits += (int) ((HashMap<?, ?>) contributor).get("total");
//                    }
//                }
//            }
        }

        log.info("커밋 조회 완료");
        log.info("총 커밋 개수는 {}개 입니다.", totalCommits);

        return totalCommits;
    }
}
