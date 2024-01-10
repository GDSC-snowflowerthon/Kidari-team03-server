package com.committers.snowflowerthon.committersserver.common.response.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 도메인
    // 타입(상태 코드, "메시지");

    // Token
    WRONG_JWT_TOKEN(HttpStatus.UNAUTHORIZED.value(),"잘못된 JWT 서명입니다."),
    LOG_OUT_JWT_TOKEN(HttpStatus.UNAUTHORIZED.value(),"로그아웃된 사용자입니다."),
    JWT_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED.value(),"JWT 토큰이 만료되었습니다."),
    JWT_TOKEN_NOT_EXISTS(HttpStatus.UNAUTHORIZED.value(),"헤더에 JWT 토큰 값이 존재하지 않습니다"),

    // Default
    ERROR(400, "요청 처리에 실패했습니다."),

    // Member
    MEMBER_NOT_FOUND(404, "존재하지 않는 사용자입니다."),

    // GitHub
    Github_Member_NOT_FOUND(404, "깃허브에 존재하지 않는 사용자입니다."),

    // Commit
    COMMIT_NOT_FOUND(404, "커밋 기록이 존재하지 않습니다."),

    // Univ
    UNIV_NOT_FOUND(404, "존재하지 않는 대학입니다."),

    // Follow
    FOLLOW_BAD_REQUEST(400, "isFollowed 쿼리 변수 값이 정확한지 확인이 필요합니다.")
    ;

    private final int statusCode;
    private final String message;
}

