package com.committers.snowflowerthon.committersserver.auth.config;

import com.committers.snowflowerthon.committersserver.auth.github.GitHubService;
import com.committers.snowflowerthon.committersserver.auth.jwt.CustomToken;
import com.committers.snowflowerthon.committersserver.auth.jwt.JwtUtils;
import com.committers.snowflowerthon.committersserver.common.response.exception.ErrorCode;
import com.committers.snowflowerthon.committersserver.common.response.exception.MemberException;
import com.committers.snowflowerthon.committersserver.domain.item.entity.ItemRepository;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import com.committers.snowflowerthon.committersserver.domain.member.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private MemberRepository memberRepository;
    private AuthService authService;
    private GitHubService gitHubService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        OAuth2MemberDto memberDto = OAuth2MemberDto.builder()
//                .gitHubId((Long) oAuth2User.getAttributes().get("id"))
                .nickname((String) oAuth2User.getAttributes().get("login"))
                .build();

        log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User);

        // 최초 로그인 판별
        Optional<Member> optionalMember = memberRepository.findByNickname(memberDto.getNickname());
        boolean isNew = false;

        if (!optionalMember.isPresent()) { // 신규 가입 유저
            // 회원가입
            optionalMember = Optional.ofNullable(authService.signUp(memberDto).orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND)));
            isNew = true;
        }

        // 로그인
        CustomToken token = authService.setToken(optionalMember.get(), response);

        if (!isNew) { // 기가입 유저
            gitHubService.resetSnowflake(optionalMember.get());
        }

        // Auth 컨트롤러로 리다이렉트
        String redirectUrl = UriComponentsBuilder.fromUriString("/api/home")
                .queryParam("accessToken", token.getAccessToken())
                .queryParam("refreshToken", token.getRefreshToken())
                .toUriString();
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
