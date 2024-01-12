package com.committers.snowflowerthon.committersserver.auth.config;

import com.committers.snowflowerthon.committersserver.auth.jwt.CustomToken;
import com.committers.snowflowerthon.committersserver.common.response.exception.ErrorCode;
import com.committers.snowflowerthon.committersserver.common.response.exception.MemberException;
import com.committers.snowflowerthon.committersserver.domain.commit.service.CommitService;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.MemberRepository;
import com.committers.snowflowerthon.committersserver.domain.member.service.AuthService;
import com.committers.snowflowerthon.committersserver.domain.member.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
    private final MemberRepository memberRepository;
    private final AuthService authService;
    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        log.info("OAuth2SuccessHandler");
        log.info("oAuth2User -> {}", oAuth2User);

        OAuth2MemberDto memberDto = OAuth2MemberDto.builder()
//                .gitHubId((Long) oAuth2User.getAttributes().get("id"))
                .nickname((String) oAuth2User.getAttributes().get("nickname"))
                .build();

        log.info("OAuth2SuccessHandler에서 memberDto -> {}" + memberDto.getNickname());
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
            memberService.refreshSnowflake(optionalMember.get());
        }

        // Auth 컨트롤러로 리다이렉트
        // 를 하지마
//        String redirectUrl = UriComponentsBuilder.fromUriString("/api/auth/redirect")
//                .queryParam("accessToken", token.getAccessToken())
//                .queryParam("refreshToken", token.getRefreshToken())
//                .toUriString();


//        getRedirectStrategy().sendRedirect(request, response, redirectUrl);


        String redirectUrl = "https://kidari.site/redirect";

        log.info("redirectUrl -> {}", redirectUrl);
        log.info("accessToken -> {}", token.getAccessToken());
        log.info("refreshToken -> {}", token.getRefreshToken());

        response = authService.login(response, token.getAccessToken(), token.getRefreshToken());

        // 별다른 대조 없이, 기등록된 닉네임이면 로그인 성공
        response.sendRedirect(redirectUrl);

        log.info("response -> {}", response.getHeader("Set-Cookie"));
        log.info("response -> {}", response.getHeaderNames());
    }
}
