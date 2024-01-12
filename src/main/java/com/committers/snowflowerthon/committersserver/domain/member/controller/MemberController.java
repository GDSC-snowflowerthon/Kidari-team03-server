package com.committers.snowflowerthon.committersserver.domain.member.controller;

import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberInfoDto;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberOtherResDto;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberSearchResDto;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "https://kidari.site")
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/home/myinfo") // 내 정보 조회
    public ResponseEntity<MemberInfoDto> getMemberInfo() {
        MemberInfoDto memberInfoDto = memberService.getMemberInfo();
        return ResponseEntity.ok(memberInfoDto);
    }

    @PatchMapping("/home/growth")
    public  ResponseEntity<?> growSnowman() {
        if (memberService.growSnowman()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(HttpStatus.valueOf(200));
    }

    @GetMapping("/buddy/search") // 유저 검색
    public ResponseEntity<MemberSearchResDto> searchMember(@RequestParam String nickname) {
        MemberSearchResDto member = memberService.searchMember(nickname); // 정확하게 입력한 경우만 찾음
        return ResponseEntity.ok(member);
    }

    @GetMapping("/user") // 유저 정보 페이지 조회
    public ResponseEntity<MemberOtherResDto> getOtherMemberInfo(@RequestParam String nickname) {
        MemberOtherResDto member = memberService.getOtherMemberInfo(nickname);
        return ResponseEntity.ok(member);
    }
}
