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
    String nickname; // 깃허브 아이디
    Long snowflake; // 보유 눈송이
    Long snowmanHeight; // 눈사람 키
    Long snowId;
    Long hatId;
    Long decoId;
    Boolean newAlarm; //새로운 알림이 있으면 true, 모두 다 확인한 상태면 false
}
