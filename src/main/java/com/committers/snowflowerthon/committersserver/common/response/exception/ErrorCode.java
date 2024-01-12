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

    // Follow
    FOLLOW_BAD_REQUEST(404, "팔로우가 존재하지 않습니다"),

    // Item
    ITEM_NOT_FOUND(404, "아이템이 존재하지 않습니다."),

    // Univ
    UNIV_NOT_FOUND(404, "존재하지 않는 대학입니다."),
    UNIV_REGISTER_BAD_REQUEST(404, "대학교 등록/취소 잘못된 요청입니다."),
    UNIV_CANNOT_BE_REGISTERED(404, "현재 등록한 대학이 있어 새로운 대학을 등록할 수 없습니다."),

    // Snowflake를 사용할 수 없음
    SNOWFLAKE_CANNOT_BE_USED(404, "현재 보유 중인 눈송이가 없습니다."),
    
    ;

    private final int statusCode;
    private final String message;
}

