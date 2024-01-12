package com.committers.snowflowerthon.committersserver.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberReadmeResDto {
    private String nickname;
    private Long snowmanHeight;
    private Long attacking;
    private Long damage;
    private Long snowId;
    private Long hatId;
    private Long decoId;
}