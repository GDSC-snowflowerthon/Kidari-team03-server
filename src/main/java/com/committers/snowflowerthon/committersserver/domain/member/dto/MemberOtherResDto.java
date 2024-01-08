package com.committers.snowflowerthon.committersserver.domain.member.dto;

import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class MemberOtherResDto { // 유저 정보 페이지 조회
    private String nickname;
    private Long snowmanHeight;
    private Long snowId;
    private Long hatId;
    private Long decoId;
    private boolean isFollowed;

    public static MemberOtherResDto toDto(Member member) {
        return MemberOtherResDto.builder()
                .nickname(member.getNickname())
                .snowmanHeight(member.getSnowmanHeight())
                .snowId(member.getItem().getSnowId())
                .hatId(member.getItem().getHatId())
                .decoId(member.getItem().getDecoId())
                .isFollowed(false) // 추후 친구여부 찾는 메소드 만들어야
                .build();
    }
}
