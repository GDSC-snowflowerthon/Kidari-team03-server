package com.committers.snowflowerthon.committersserver.domain.member.dto;

import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class MemberOwnResDto { // 내 정보 조회
    private String nickname;
    private Long snowflake;
    private Long snowmanHeight;
    private Long snowId;
    private Long hatId;
    private Long decoId;
    private boolean newAlarm;

    public static MemberOwnResDto toDto(Member member) {
        return MemberOwnResDto.builder()
                .nickname(member.getNickname())
                .snowflake(member.getSnowflake())
                .snowmanHeight(member.getSnowmanHeight())
                .snowId(member.getItem().getSnowId())
                .hatId(member.getItem().getHatId())
                .decoId(member.getItem().getDecoId())
                .newAlarm(member.getNewAlarm())
                .build();
    }
}