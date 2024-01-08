package com.committers.snowflowerthon.committersserver.domain.member.controller;

import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberOtherResDto;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberOwnResDto;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberSearchResDto;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    /*public ResponseEntity<MemberOwnResDto> getMyInfo() {
        MemberOwnResDto ownMember = MemberOwnResDto.toDto(member);
        if (myInfo = null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(myInfo);
    }*/

    @GetMapping("/buddy/search") // 유저 검색
    public ResponseEntity<MemberSearchResDto> searchMember(@RequestParam String nickname) {
        Member member = memberService.getMemberByNickname(nickname);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        MemberSearchResDto searchedMember = MemberSearchResDto.toDto(member);
        return ResponseEntity.ok(searchedMember);
    }

    @GetMapping("/user") // 유저 정보 페이지 조회
    public ResponseEntity<MemberOtherResDto> getMemberInfo(@RequestParam String nickname) {
        Member member = memberService.getMemberByNickname(nickname);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        MemberOtherResDto otherMember = MemberOtherResDto.toDto(member);
        return ResponseEntity.ok(otherMember);
    }

}