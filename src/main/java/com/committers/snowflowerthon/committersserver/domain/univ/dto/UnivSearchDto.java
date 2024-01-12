package com.committers.snowflowerthon.committersserver.domain.univ.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnivSearchDto {
    private String univName;
    private Long totalHeight;
    private Boolean isRegistered;
}
