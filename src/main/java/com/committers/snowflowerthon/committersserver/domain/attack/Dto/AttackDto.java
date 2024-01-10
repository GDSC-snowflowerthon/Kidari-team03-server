package com.committers.snowflowerthon.committersserver.domain.attack.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AttackDto {
    private String time; // 날짜 포맷 위해 Date 아닌 String 으로 전달
    private String attacker;
    private Boolean isChecked;
}