package com.committers.snowflowerthon.committersserver.domain.univ.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
public class UnivUpdateResDto {
    private int status;
    private String message;
    private List<UnivRegisterResultDto> data;

    public UnivUpdateResDto univRegisterSuccess(String univName, Boolean isRegistered) {
        List<UnivRegisterResultDto> univData = Collections.singletonList(new UnivRegisterResultDto(univName, isRegistered));
        UnivUpdateResDto response = UnivUpdateResDto.builder()
                .status(200)
                .message("학교 등록이 완료되었습니다.")
                .data(univData)
                .build();
        return response;
    }
}
