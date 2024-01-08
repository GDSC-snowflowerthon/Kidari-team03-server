package com.committers.snowflowerthon.committersserver.domain.member.dto;

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
}
