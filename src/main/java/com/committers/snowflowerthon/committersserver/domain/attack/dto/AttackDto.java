package com.committers.snowflowerthon.committersserver.domain.attack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AttackDto {
    private String time;
    private String attacker;
    private Boolean isChecked;
}