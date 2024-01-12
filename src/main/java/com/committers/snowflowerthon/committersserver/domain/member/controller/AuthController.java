package com.committers.snowflowerthon.committersserver.domain.member.controller;

import com.committers.snowflowerthon.committersserver.domain.member.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

//    @GetMapping("/api/auth/redirect") // 인증 성공 시 리다이렉트 경로
//    public void login(HttpServletRequest request, HttpServletResponse response,
//                                         @RequestParam("accessToken") String accessToken,
//                                         @RequestParam("refreshToken") String refreshToken) throws IOException {
//
//        log.info("redirect to /auth/redirect");
//
//        String redirectUrl = "https://kidari.site/redirect";
//
//        log.info("AuthController의 login");
//        log.info("accessToken -> {}", accessToken);
//        log.info("refreshToken -> {}", refreshToken);
//
//        HttpServletResponse response1 = authService.login(response, accessToken, refreshToken);
//
//        // 별다른 대조 없이, 기등록된 닉네임이면 로그인 성공
//        response1.sendRedirect(redirectUrl);
//
//        log.info("response -> {}", response1.getHeader("Set-Cookie"));
//        log.info("response -> {}", response1.getHeaderNames());
//
//    }

}
