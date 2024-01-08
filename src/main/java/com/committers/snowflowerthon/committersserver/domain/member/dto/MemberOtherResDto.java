package com.committers.snowflowerthon.committersserver.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class MemberOtherResDto { // 다른 유저 조회
    private String nickname;
    private Long snowmanHeight;
    private Long snowId;
    private Long hatId;
    private Long decoId;
}
