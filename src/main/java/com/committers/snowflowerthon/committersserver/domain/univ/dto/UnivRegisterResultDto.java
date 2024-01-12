package com.committers.snowflowerthon.committersserver.domain.univ.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnivRegisterResultDto {
    private String univName;
    private Boolean isRegistered;
}
