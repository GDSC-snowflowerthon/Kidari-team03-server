package com.committers.snowflowerthon.committersserver.domain.follow.controller;

import com.committers.snowflowerthon.committersserver.domain.follow.dto.FollowPatchedDto;
import com.committers.snowflowerthon.committersserver.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://kidari.site")
@RequestMapping("/api/v1")
public class FollowController {
    private final FollowService followService;

    @PatchMapping("/buddy/update")
    public ResponseEntity<FollowPatchedDto> patchFollow(@RequestParam String nickname, @RequestParam boolean isFollowed) {
        FollowPatchedDto followPatchedDto = followService.changeFollowStatus(nickname, isFollowed);
        return ResponseEntity.ok(followPatchedDto);
    }
}