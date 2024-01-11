package com.committers.snowflowerthon.committersserver.common.response.exception;

public class ItemException extends DefaultException {
    public ItemException(ErrorCode statusCode) {
        super(statusCode);
    }
}
