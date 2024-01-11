package com.committers.snowflowerthon.committersserver.common.response.exception;

public class TokenException extends DefaultException {
    public TokenException(ErrorCode statusCode) {
        super(statusCode);
    }
}