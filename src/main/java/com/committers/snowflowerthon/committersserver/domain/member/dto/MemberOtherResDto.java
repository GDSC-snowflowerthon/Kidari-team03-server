package com.committers.snowflowerthon.committersserver.domain.member.dto;

import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberOtherResDto { // 유저 정보 페이지 조회
    private String nickname;
    private Long snowmanHeight;
    private Long snowId;
    private Long hatId;
    private Long decoId;
    private boolean isFollowed;

    public static MemberOtherResDto toDto(Member member, Boolean followStatus) {
        return MemberOtherResDto.builder()
                .nickname(member.getNickname())
                .snowmanHeight(member.getSnowmanHeight())
                .snowId(member.getItem().getSnowId())
                .hatId(member.getItem().getHatId())
                .decoId(member.getItem().getDecoId())
                .isFollowed(followStatus)
                .build();
    }
}