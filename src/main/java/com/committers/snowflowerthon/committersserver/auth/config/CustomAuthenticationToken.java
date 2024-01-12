package com.committers.snowflowerthon.committersserver.auth.config;

import com.committers.snowflowerthon.committersserver.common.validation.ValidationService;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Role;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String nickname;
//    private final Member member;
    private final Role role;

    public CustomAuthenticationToken(String nickname, Member member, Role role, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(member, credentials, authorities);
        this.nickname = nickname;
//        this.member = member;
        this.role = role;
    }
}
