package com.committers.snowflowerthon.committersserver.domain.follow.controller;

import com.committers.snowflowerthon.committersserver.common.response.ApiResponse;
import com.committers.snowflowerthon.committersserver.common.response.exception.ErrorCode;
import com.committers.snowflowerthon.committersserver.domain.follow.dto.FollowPatchedDto;
import com.committers.snowflowerthon.committersserver.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://kidari.site", "https://www.kidari.site", "http://localhost:5173"}, allowedHeaders = "*")
@RequestMapping("/api/v1")
public class FollowController {

    private final FollowService followService;

    @PatchMapping("/buddy/update")
    public ResponseEntity<?> patchFollow(@RequestParam String nickname, @RequestParam Boolean isFollowed) {
        FollowPatchedDto followPatchedDto = followService.changeFollowStatus(nickname, isFollowed);
        if (followPatchedDto == null)
            return ResponseEntity.badRequest().body(ApiResponse.failure(ErrorCode.FOLLOW_BAD_REQUEST));
        return ResponseEntity.ok().body(ApiResponse.success(followPatchedDto));
    }
}