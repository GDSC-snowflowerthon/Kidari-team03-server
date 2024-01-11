package com.committers.snowflowerthon.committersserver.common.response.exception;

public class MemberException extends DefaultException {
    public MemberException(ErrorCode statusCode) {
        super(statusCode);
    }
}
