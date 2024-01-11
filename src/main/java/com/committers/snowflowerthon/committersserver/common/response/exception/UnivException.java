package com.committers.snowflowerthon.committersserver.common.response.exception;

public class UnivException extends DefaultException {
    public UnivException(ErrorCode statusCode) {
        super(statusCode);
    }
}
