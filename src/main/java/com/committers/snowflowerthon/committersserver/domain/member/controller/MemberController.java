package com.committers.snowflowerthon.committersserver.domain.member.controller;

import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberOtherResDto;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberOwnResDto;
import com.committers.snowflowerthon.committersserver.domain.member.dto.MemberSearchResDto;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    /*
    // 내 정보 받아오는 부분 필요
     */

    @PatchMapping("/home/growth")
    public  ResponseEntity<?> growSnowman(Long id) {
        Member member = memberService.getMemberById(id);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        if (member.useSnowflake()) { // 눈송이 소모
            member.growSnowmanHeight(); // 키 키우기
            return ResponseEntity.ok(HttpStatus.valueOf(200));
        } else {
            return ResponseEntity.badRequest().build(); //눈송이를 사용할 수 없음
        }
    }

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
