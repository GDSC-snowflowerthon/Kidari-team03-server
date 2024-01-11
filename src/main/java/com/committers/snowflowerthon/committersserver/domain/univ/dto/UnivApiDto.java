package com.committers.snowflowerthon.committersserver.domain.univ.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UnivApiDto {
    private String web_page;
    private String country;
    private String domain;
    private String name;
}
