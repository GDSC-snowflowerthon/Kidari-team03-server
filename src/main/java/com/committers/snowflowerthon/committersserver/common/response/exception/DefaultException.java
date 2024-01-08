package com.committers.snowflowerthon.committersserver.common.response.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DefaultException extends RuntimeException {
    private ErrorCode errorCode;

    public DefaultException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
