package com.committers.snowflowerthon.committersserver.auth.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
public class CustomToken {

    private String accessToken;
    private String refreshToken;

}
