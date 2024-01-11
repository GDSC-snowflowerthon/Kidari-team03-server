package com.committers.snowflowerthon.committersserver.common.response.exception;

public class CommitException extends DefaultException {
    public CommitException(ErrorCode statusCode) {
        super(statusCode);
    }
}
