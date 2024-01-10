package com.committers.snowflowerthon.committersserver.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomResponseDto {
    private final int statusCode;
    private final String message;
    private final Object data;

    @Builder
    private CustomResponseDto(SuccessCode statusCode, Object data) {
        this.statusCode = statusCode.getStatusCode();
        this.message = statusCode.getMessage();
        this.data = data;
    }

    public static CustomResponseDto of(SuccessCode statusCode, Object data){
        return CustomResponseDto.builder()
                .statusCode(statusCode)
                .data(data)
                .build();
    }

    public static CustomResponseDto from(SuccessCode statusCode){
        return CustomResponseDto.builder()
                .statusCode(statusCode)
                .build();
    }
}
