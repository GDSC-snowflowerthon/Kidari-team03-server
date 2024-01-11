package com.committers.snowflowerthon.committersserver.domain.follow.controller;

import com.committers.snowflowerthon.committersserver.domain.follow.dto.FollowPatchedDto;
import com.committers.snowflowerthon.committersserver.domain.follow.service.FollowService;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FollowController {
    private final FollowService followService;

    @PatchMapping("/buddy/update")
    public ResponseEntity<FollowPatchedDto> patchFollow(@RequestParam String nickname, @RequestParam boolean isFollowed) {
        FollowPatchedDto followPatchedDto = followService.changeFollowStatus(nickname, isFollowed);
        return ResponseEntity.ok(followPatchedDto);
    }
}