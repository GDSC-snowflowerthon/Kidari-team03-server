package com.committers.snowflowerthon.committersserver.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    // 도메인
    // 타입(상태 코드, "메시지");

    // Token
//    ACCESS_TOKEN_REISSUANCE_SUCCESS(200, "AccessToken 재발급에 성공했습니다."),
//    REFRESH_TOKEN_NOT_FOUND(400,"유효한 RefreshToken이 없습니다"),
//    ACCESS_TOKEN_EXPIRARION(401, "토큰이 만료되었습니다."),
    LOG_IN_SUCCESS(200, "로그인에 성공했습니다."),

    // Default
    SUCCESS(200, "요청 처리에 성공했습니다."),

    // Member
    SIGN_UP_SUCCESS(200, "회원 가입에 성공했습니다."),
    DELETE_MEMBER_SUCCESS(200, "회원 탈퇴가 완료 되었습니다."),

    // GitHub

    // Redis
    SET_REDIS_KEY_SUCCESS(201, "레디스 키 등록에 성공했습니다."),
    GET_REDIS_KEY_SUCCESS(200, "레디스 키 조회에 성공했습니다.");

    private final int statusCode;
    private final String message;
}
