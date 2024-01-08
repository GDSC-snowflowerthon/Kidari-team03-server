package com.committers.snowflowerthon.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role { // 유저 권한(게스트/멤버)

    GUEST("ROLE_GUEST", "게스트"),
    MEMBER("ROLE_MEMBER", "일반 사용자");

    private final String role;
    private final String title;
}
