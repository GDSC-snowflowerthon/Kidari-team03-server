package com.committers.snowflowerthon.committersserver.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role { // 유저 권한(게스트/멤버)

    ROLE_GUEST("ROLE_GUEST", "게스트"),
    ROLE_MEMBER("ROLE_MEMBER", "일반 사용자");

    private final String role;
    private final String title;

    public static Role getDefaultRole() {
        return ROLE_GUEST; // 기본값은 ROLE_GUEST로 설정
    }
}
