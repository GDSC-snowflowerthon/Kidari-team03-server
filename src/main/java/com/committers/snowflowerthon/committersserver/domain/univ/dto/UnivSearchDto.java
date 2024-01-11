package com.committers.snowflowerthon.committersserver.domain.univ.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UnivSearchDto {
    private String univName;
    private Long totalHeight;
    private Boolean isRegistered;
}
