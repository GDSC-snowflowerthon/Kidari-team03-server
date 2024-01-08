package com.committers.snowflowerthon.committersserver.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider /*implements AuthenticationProvider*/ {

    /*
    private final CustomUserDetailsService userDetailsService;
    public static final Long ACCESS_VAL_TIME = 1800L; // 30분
    public static final Long REFESH_VAL_TIME = 14 * 24 * 60 * 60L; // 14일

    @Value("${secret.key}")
    private String secretKey;

    @Value("${secret.issuer")
    private String issuer;

    // 주어진 Authentication 객체를 기반으로 사용자를 인증
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    // 지원하는 Authentication 객체 클래스를 지정
    // 여기서는 항상 false를 반환하여 이 프로바이더가 지정한 클래스를 지원하지 않음을 나타냄
    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

    // 시크릿 키를 사용하여 HMAC256 알고리즘으로 서명하는 데 사용되는 Algorithm 객체를 반환
    private Algorithm getSigningKey(String secretKey) {
        return Algorithm.HMAC256(secretKey);
    }

    // 주어진 JWT에서 모든 클레임(claim)을 가져와 맵으로 반환
    private Map<String, Claim> getAllClaims(DecodedJWT token) {
        return token.getClaims();
    }

    // 주어진 토큰에서 "gitHubId" 클레임을 가져와 반환
    public String getGitHubIdFromToken(String token) {
        DecodedJWT verifiedToken = validateToken(token);
        return verifiedToken.getClaim("githubId").asString();
    }

    // 주어진 refresh 토큰에서 "githubId" 클레임을 가져와 반환
    public String getRefreshGitHubIdFromToken(String token) {
        DecodedJWT verifiedToken = RefreshvalidateToken(token);
        return verifiedToken.getClaim("githubId").asString();
    }

    // 토큰의 유효성을 검사하기 위한 JWTVerifier를 반환
    private JWTVerifier getTokenValidator() {
        return JWT.require(getSigningKey(secretKey))
                .withIssuer(issuer)
                .acceptExpiresAt(ACCESS_VAL_TIME)
                .build();
    }

    // refresh 토큰의 유효성을 검사하기 위한 JWTVerifier를 반환
    private JWTVerifier RefreshgetTokenValidator() {
        return JWT.require(getSigningKey(secretKey))
                .withIssuer(issuer)
                .acceptExpiresAt(REFESH_VAL_TIME)
                .build();
    }


    // 주어진 페이로드로 토큰을 생성
    public String generateToken(Map<String, String> payload) {

        return doGenerateToken(ACCESS_VAL_TIME, payload);
    }

    // 주어진 페이로드로 refresh 토큰을 생성
    public String generateRefreshToken(Map<String, String> payload) {
        return doGenerateToken(REFESH_VAL_TIME, payload);
    }

    // 주어진 만료 시간 및 페이로드로 토큰을 생성
    private String doGenerateToken(long expireTime, Map<String, String> payload) {

        return JWT.create()
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .withPayload(payload)
                .withIssuer(issuer)
                .sign(getSigningKey(secretKey));
    }

    // 주어진 토큰의 유효성을 검사하고 유효한 경우 디코딩된 JWT를 반환
    private DecodedJWT validateToken(String token) throws JWTVerificationException {
        JWTVerifier validator = getTokenValidator();
        return validator.verify(token);
    }

    // 주어진 refresh 토큰의 유효성을 검사하고 유효한 경우 디코딩된 JWT를 반환
    private DecodedJWT RefreshvalidateToken(String token) throws JWTVerificationException {
        JWTVerifier validator = RefreshgetTokenValidator();
        return validator.verify(token);
    }

    // 주어진 access 토큰이 만료되었는지 확인
    public boolean isTokenExpired(String token) {
        try {
            DecodedJWT decodedJWT = validateToken(token);
            return false;
        } catch (JWTVerificationException e) {
            log.info("accessToken 만료");
            return true;
        }
    }

    // 주어진 refresh 토큰이 만료되었는지 확인
    public boolean isRefreshTokenExpired(String token) {
        try {
            DecodedJWT decodedJWT = RefreshvalidateToken(token);
            return false;
        } catch (JWTVerificationException e) {
            log.info("refresh 토큰 만료");
            return true;
        }
    }
     */
}
