package com.committers.snowflowerthon.committersserver.auth.config;

import com.committers.snowflowerthon.committersserver.domain.member.entity.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtils {

    public static String getNickname() {

        Authentication authentication = getAuthentication();

        if (authentication != null) {
            return getCustomAuthenticationToken(authentication).getName();
        }
        // 커스텀 필요
        throw new UsernameNotFoundException("Nickname not found in the current Authentication context");
    }

    public static Long getMemberId() {

        Authentication authentication = getAuthentication();

        if (authentication != null) {
            return getCustomAuthenticationToken(authentication).getMemberId();
        }
        // 커스텀 필요
        throw new UsernameNotFoundException("Member ID not found in the current Authentication context");
    }

    public static Role getRole() {
        Authentication authentication = getAuthentication();

        if(authentication != null) {
            return getCustomAuthenticationToken(authentication).getRole();
        }
        // 커스텀 필요
        throw new UsernameNotFoundException("Role not found in the current Authentication context");
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private static CustomAuthenticationToken getCustomAuthenticationToken(Authentication authentication) {
        return (CustomAuthenticationToken) authentication.getPrincipal();
    }
}