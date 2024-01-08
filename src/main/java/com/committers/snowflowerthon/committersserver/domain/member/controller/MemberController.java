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
        MemberOwnResDto myInfo = memberService.getMemberById();
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
        MemberSearchResDto searchedMember = MemberSearchResDto.builder()
                .nickname(member.getNickname())
                .snowmanHeight(member.getSnowmanHeight())
                .isFollowed(false) // 추후 친구여부 찾는 메소드 만들어야
                .build();
        return ResponseEntity.ok(searchedMember);
    }

    @GetMapping("/user") // 유저 정보 페이지 조회
    public ResponseEntity<MemberOtherResDto> getMemberInfo(@RequestParam String nickname) {
        Member member = memberService.getMemberByNickname(nickname);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        MemberOtherResDto otherMember = MemberOtherResDto.builder()
                .nickname(member.getNickname())
                .snowmanHeight(member.getSnowmanHeight())
                .snowId(member.getItem().getSnowId())
                .hatId(member.getItem().getHatId())
                .decoId(member.getItem().getDecoId())
                .isFollowed(false) // 추후 친구여부 찾는 메소드 만들어야
                .build();
        return ResponseEntity.ok(otherMember);
    }

}