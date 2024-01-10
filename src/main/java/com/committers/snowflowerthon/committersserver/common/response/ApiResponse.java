package com.committers.snowflowerthon.committersserver.common.response;

import com.committers.snowflowerthon.committersserver.common.response.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class ApiResponse<T> {

    private final int statusCode;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(
                SuccessCode.SUCCESS.getStatusCode(),
                SuccessCode.SUCCESS.getMessage(),
                null
        );
    }

    public static <T> ApiResponse<T> success(SuccessCode successCode) {
        return new ApiResponse<>(
                successCode.getStatusCode(),
                successCode.getMessage(),
                null
        );
    }

    public static <T> ApiResponse<T> success(SuccessCode successCode, T data) {
        return new ApiResponse<>(
                successCode.getStatusCode(),
                successCode.getMessage(),
                data
        );
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                SuccessCode.SUCCESS.getStatusCode(),
                SuccessCode.SUCCESS.getMessage(),
                data
        );
    }

    public static <T> ApiResponse<List<T>> success(List<T> data) {
        return new ApiResponse<>(
                SuccessCode.SUCCESS.getStatusCode(),
                SuccessCode.SUCCESS.getMessage(),
                data
        );
    }

    public static <T> ApiResponse<T> failure(ErrorCode statusCode) {
        return new ApiResponse<>(
                statusCode.getStatusCode(),
                statusCode.getMessage(),
                null
        );
    }

    public static <T> ApiResponse<T> failure(ErrorCode statusCode, String message) {
        return new ApiResponse<>(
                statusCode.getStatusCode(),
                message,
                null
        );
    }
}
