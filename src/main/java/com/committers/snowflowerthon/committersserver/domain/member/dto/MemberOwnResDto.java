package com.committers.snowflowerthon.committersserver.domain.member.dto;

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
}