package com.committers.snowflowerthon.committersserver.domain.member.controller;

import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberOtherResDto;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberSearchResDto;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /*
    // 내 정보 받아오는 부분 필요
     */

    @PatchMapping("/home/growth")
    public  ResponseEntity<?> growSnowman(Long id) {
        if (!memberService.growSnowman(id)) {
            return ResponseEntity.badRequest().build(); //눈송이를 사용할 수 없음
        }
        return ResponseEntity.ok(HttpStatus.valueOf(200));
    }

    @GetMapping("/buddy/search") // 유저 검색
    public ResponseEntity<MemberSearchResDto> searchMember(@RequestParam String nickname) {
        MemberSearchResDto member = memberService.searchMember(nickname);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(member);
    }

    @GetMapping("/user") // 유저 정보 페이지 조회
    public ResponseEntity<MemberOtherResDto> getMemberInfo(@RequestParam String nickname) {
        MemberOtherResDto member = memberService.getOtherMember(nickname);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(member);
    }
}
