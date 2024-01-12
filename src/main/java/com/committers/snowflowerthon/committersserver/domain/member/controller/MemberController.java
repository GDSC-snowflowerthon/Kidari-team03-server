package com.committers.snowflowerthon.committersserver.domain.member.controller;

import com.committers.snowflowerthon.committersserver.common.response.ApiResponse;
import com.committers.snowflowerthon.committersserver.common.response.exception.ErrorCode;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberInfoDto;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberOtherResDto;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberSearchResDto;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"https://kidari.site", "https://www.kidari.site", "http://localhost:5173"}, allowedHeaders = "*")
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/home/myinfo") // 내 정보 조회
    public ResponseEntity<?> getMemberInfo() {
        MemberInfoDto memberInfoDto = memberService.getMemberInfo();
        return ResponseEntity.ok().body(ApiResponse.success(memberInfoDto));
    }

    @PatchMapping("/home/growth")
    public  ResponseEntity<?> growSnowman() {
        if (memberService.growSnowman()) { //눈사람 키우기 성공
            return ResponseEntity.ok().body(ApiResponse.success());
        } // 눈송이가 없음
        return ResponseEntity.badRequest().body(ApiResponse.failure(ErrorCode.SNOWFLAKE_CANNOT_BE_USED));
    }

    @GetMapping("/buddy/search") // 유저 검색
    public ResponseEntity<?> searchMember(@RequestParam String nickname) {
        MemberSearchResDto member = memberService.searchMember(nickname); // 정확하게 입력한 경우만 찾음
        if (member == null) { // 찾아진 유저가 없음 (에러는 안 띄움)
            return ResponseEntity.ok().body(ApiResponse.success());
        }
        return ResponseEntity.ok().body(ApiResponse.success(member));
    }

    @GetMapping("/user") // 유저 정보 페이지 조회
    public ResponseEntity<?> getOtherMemberInfo(@RequestParam String nickname) {
        MemberOtherResDto member = memberService.getOtherMemberInfo(nickname);
        if (member == null) { // 찾아진 유저가 없음 (에러 띄움)
            return ResponseEntity.badRequest().body(ApiResponse.failure(ErrorCode.MEMBER_NOT_FOUND));
        }
        return ResponseEntity.ok().body(ApiResponse.success(member));
    }
}
