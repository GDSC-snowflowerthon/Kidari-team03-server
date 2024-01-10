package com.committers.snowflowerthon.committersserver.auth.config;

import com.committers.snowflowerthon.committersserver.auth.jwt.JwtUtils;
import com.committers.snowflowerthon.committersserver.common.response.exception.ErrorCode;
import com.committers.snowflowerthon.committersserver.common.response.exception.MemberException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private JwtUtils jwtUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        
        // access 토큰 획득
        String accessToken = jwtUtils.resolveAccessToken(request);
        
        if (jwtUtils.validateToken(accessToken)) {
            // nickname 획득
            String nickname = jwtUtils.getNicknameFromToken(accessToken);
            // nickname으로 refresh 토큰 삭제
            jwtUtils.deleteRefreshTokenByNickname(nickname);
            // access 토큰 비활성화
            jwtUtils.setBlackList(accessToken);
        } else { // access 토큰 검증 실패
            throw new MemberException(ErrorCode.WRONG_JWT_TOKEN);
        }

        // 적절한 응답 전송
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().flush();

        // 리다이렉트 어디로?
        String redirectUrl = "https://kidari.site/redirect";
        response.sendRedirect(redirectUrl);
    }
}
