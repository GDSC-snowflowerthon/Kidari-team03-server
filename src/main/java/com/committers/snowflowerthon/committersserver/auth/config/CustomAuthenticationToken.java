package com.committers.snowflowerthon.committersserver.auth.config;

import com.committers.snowflowerthon.committersserver.domain.member.entity.Role;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String nickname;
    private final Long memberId;
    private final Role role;

    public CustomAuthenticationToken(String nickname, Long memberId, Role role, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(memberId, credentials, authorities);
        this.nickname = nickname;
        this.memberId = memberId;
        this.role = role;
    }
}
