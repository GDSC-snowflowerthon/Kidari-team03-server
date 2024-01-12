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
            return String.valueOf(getCustomAuthenticationToken(authentication).getName());
        }
        // 커스텀 필요
        throw new UsernameNotFoundException("Nickname not found in the current Authentication context");
    }

    public static Long getMemberId() {

        Authentication authentication = getAuthentication();

        if (authentication != null) {
            return Long.valueOf(getCustomAuthenticationToken(authentication).getMemberId());
        }
        // 커스텀 필요
        throw new UsernameNotFoundException("Member ID not found in the current Authentication context");
    }

    public static Role getRole() {
        Authentication authentication = getAuthentication();

        if(authentication != null) {
            return Role.valueOf(getCustomAuthenticationToken(authentication).getRole().toString());
        }
        // 커스텀 필요
        throw new UsernameNotFoundException("Role not found in the current Authentication context");
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private static CustomAuthenticationToken getCustomAuthenticationToken(Authentication authentication) {
//        return (CustomAuthenticationToken) authentication.getPrincipal();
        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomAuthenticationToken) {
            return (CustomAuthenticationToken) principal;
        } else {
            // 처리할 로직이나 예외를 처리하는 코드를 추가할 수 있습니다.
            throw new IllegalStateException("Unexpected principal type: " + principal.getClass());
        }
    }
}