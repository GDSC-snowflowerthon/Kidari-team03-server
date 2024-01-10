package com.committers.snowflowerthon.committersserver.auth.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OAuth2MemberDto {

//    private Long gitHubId;
    private String nickname;
}
