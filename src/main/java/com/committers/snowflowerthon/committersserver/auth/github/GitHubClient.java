package com.committers.snowflowerthon.committersserver.auth.github;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "gitHubClient", url = "https://api.github.com")
public interface GitHubClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{nickname}/repos")
    List<RepoResponseDto> getRepo(@PathVariable("nickname") String nickname,
                                  @RequestParam("page") int page,
                                  @RequestParam("perPage") int perPage);

    @RequestMapping(method = RequestMethod.GET, value = "/repos/{repo}/commits")
        List<CommitResponseDto> getCommit(@PathVariable("repo") String repo);
}
