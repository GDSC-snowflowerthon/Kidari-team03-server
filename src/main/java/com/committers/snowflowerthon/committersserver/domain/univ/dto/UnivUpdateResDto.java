package com.committers.snowflowerthon.committersserver.domain.univ.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UnivUpdateResDto {
    private int status;
    private String message;
    private List<UnivRegisterResultDto> data;
}
