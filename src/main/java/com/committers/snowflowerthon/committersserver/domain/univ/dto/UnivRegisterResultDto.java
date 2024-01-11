package com.committers.snowflowerthon.committersserver.domain.univ.dto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UnivRegisterResultDto {
    private String univName;
    private Boolean isRegistered;
}
