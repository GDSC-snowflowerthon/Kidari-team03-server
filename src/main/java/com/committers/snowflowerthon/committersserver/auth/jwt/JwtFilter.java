package com.committers.snowflowerthon.committersserver.auth.jwt;

import com.committers.snowflowerthon.committersserver.auth.config.CustomAuthenticationToken;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtUtils.resolveAccessToken(request);
        if (jwtUtils.validateToken(accessToken)) {
            setAuthentication(accessToken);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String accessToken) {
        String nickname = jwtUtils.getNicknameFromToken(accessToken);
        Long memberId = jwtUtils.getMemberIdFromToken(accessToken);
        Role role = jwtUtils.getRoleFromToken(accessToken);

        CustomAuthenticationToken authenticationToken = new CustomAuthenticationToken(
                nickname,
                memberId,
                role,
                "",        // credentials
                List.of(new SimpleGrantedAuthority(role.toString()))
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth")
                || path.equals("/api/auth/redirect")
                || path.equals("/login/oauth2/code/github")
                || path.equals("/health")
                ;
    }
}
