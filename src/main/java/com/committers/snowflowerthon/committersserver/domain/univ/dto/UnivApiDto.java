package com.committers.snowflowerthon.committersserver.domain.univ.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnivApiDto {
    private String web_page;
    private String country;
    private String domain;
    private String name;
}
