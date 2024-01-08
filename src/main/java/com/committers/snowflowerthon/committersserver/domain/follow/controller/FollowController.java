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
public class FollowController {
    private final FollowService followService;
    private final MemberService memberService;

    @PatchMapping("/buddy/update")
    public ResponseEntity<FollowPatchedDto> patchFollow(@RequestParam String nickname, @RequestParam boolean isFollowed) {
        Member buddy = memberService.getMemberByNickname(nickname);
        if (buddy == null) {
            return ResponseEntity.notFound().build(); //에러 처리 필요
        }
        followService.changeFollowStatus(buddy.getId(), isFollowed);
        //아래는 검증용
        FollowPatchedDto updateDto = new FollowPatchedDto(nickname, isFollowed);
        return new ResponseEntity<FollowPatchedDto>(HttpStatus.CREATED);
    }
}