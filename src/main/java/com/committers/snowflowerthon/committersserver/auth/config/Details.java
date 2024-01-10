package com.committers.snowflowerthon.committersserver.auth.config;

import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

@Slf4j
public class Details extends User { //Authentication 객체 생성 super()을 이용하여!
    public Details(Member member) {

        super(String.valueOf(member.getId()),member.getNickname(),
                AuthorityUtils.createAuthorityList(String.valueOf(member.getRole())));
    }
}
