package com.committers.snowflowerthon.committersserver.auth.jwt;

import com.committers.snowflowerthon.committersserver.common.response.exception.ErrorCode;
import com.committers.snowflowerthon.committersserver.common.response.exception.TokenException;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Member;
import com.committers.snowflowerthon.committersserver.domain.member.entity.Role;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class JwtUtils {

    @Value("${secret.time.access}")
    private long accessTokenTime; // 30분;
    @Value("${secret.time.refresh}")
    private long refreshTokenTime; // 14일;
    @Value("${secret.key}")
    private String jwtSecretKey;
    private final StringRedisTemplate stringRedisTemplate;

    public String createAccessToken(Member member) {
        Claims claims = Jwts.claims();
        claims.put("nickname", member.getNickname());
        claims.put("memberId", member.getId());
        claims.put("role", member.getRole());
        long validTime = accessTokenTime;
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }


    public String createRefreshToken(Member member) {
        Claims claims = Jwts.claims();
        claims.put("nickname", member.getNickname());
        claims.put("memberId", member.getId());
        long validTime = refreshTokenTime;
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public void updateUserRefreshToken(Member member, String refreshToken) {
        stringRedisTemplate.opsForValue().set(member.getNickname(), refreshToken, refreshTokenTime, TimeUnit.MILLISECONDS);
    }

    public String getUserRefreshToken(String nickname) {
        return stringRedisTemplate.opsForValue().get(nickname);
    }

    public void deleteRefreshTokenByNickname(String nickname) {
        if (getUserRefreshToken(nickname) != null) {
            stringRedisTemplate.delete(nickname);
        }
    }

    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
//            throw new AppException(ErrorCode.JWT_TOKEN_NOT_EXISTS);
//            throw new TokenException(ErrorCode.JWT_TOKEN_NOT_EXISTS);
            return false;
        }
        if(isLogout(token)){
//            throw new AppException(ErrorCode.JWT_TOKEN_EXPIRED);
            throw new TokenException(ErrorCode.LOG_OUT_JWT_TOKEN);
        }
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
            return true;
        } catch (MalformedJwtException e) {
//            throw new AppException(ErrorCode.WRONG_JWT_TOKEN);
            throw new TokenException(ErrorCode.WRONG_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
//            throw new AppException(ErrorCode.JWT_TOKEN_EXPIRED);
            throw new TokenException(ErrorCode.JWT_TOKEN_EXPIRED);
        }
    }

    public void setBlackList(String accessToken) {
        Long expiration = getExpiration(accessToken);
        stringRedisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
    }

    public boolean isLogout(String accessToken) {
        return !ObjectUtils.isEmpty(stringRedisTemplate.opsForValue().get(accessToken));
    }

    public Long getExpiration(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.getTime() - new Date().getTime();
    }

    public String getNicknameFromToken(String token) {
        return (String) getClaims(token).get("nickname");
    }

    public Long getMemberIdFromToken(String token) {
        return (Long) getClaims(token).get("memberId");
    }

    public Role getRoleFromToken(String token) {
        return (Role) getClaims(token).get("role");
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String jwtHeader = request.getHeader("Authorization");
        if (jwtHeader != null && jwtHeader.startsWith("Bearer ")) {
            return jwtHeader.replace("Bearer ", "");
        } else {
            return null;
        }
    }
}
