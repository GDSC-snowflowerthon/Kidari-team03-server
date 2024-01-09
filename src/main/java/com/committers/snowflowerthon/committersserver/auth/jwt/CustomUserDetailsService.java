package com.committers.snowflowerthon.committersserver.auth.jwt;

import com.committers.snowflowerthon.committersserver.auth.config.Details;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {

        Member member = memberRepository.findByNickname(nickname).orElseGet(() -> Member.builder().build());

        log.info(nickname);
        log.info(String.valueOf(member));

        if(member == null) {
            throw new UsernameNotFoundException(nickname + ": 존재하지 않는 사용자입니다.");
        }

        return new Details(member);
    }
}
