package com.committers.snowflowerthon.committersserver.common.response.exception;

public class GitHubException extends DefaultException {
    public GitHubException(ErrorCode statusCode) {
        super(statusCode);
    }
}
