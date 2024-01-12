package com.committers.snowflowerthon.committersserver.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoDto {
    String nickname;
    Long snowflake;
    Long snowmanHeight;
    Long snowId;
    Long hatId;
    Long decoId;
    Boolean newAlarm;
}
