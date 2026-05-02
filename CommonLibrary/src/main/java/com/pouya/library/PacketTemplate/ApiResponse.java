package com.pouya.library.PacketTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;
    private final Integer code;

    public ApiResponse(boolean success, String message, T data, Integer code) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Integer getCode() {
        return code;
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        ApiResponse<T> response = new ApiResponse<>(true, "Done", data, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>(true, message, data, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
    public static <T> ResponseEntity<ApiResponse<T>> error(String message) {
        ApiResponse<T> response = new ApiResponse<>(false, message, null, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(response);
    }
    public static <T> ResponseEntity<ApiResponse<T>> error(String message, HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>(false, message, null, status.value());
        return ResponseEntity.status(status).body(response);
    }
    public static <T> ResponseEntity<ApiResponse<T>> error(T data,String message, HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>(false, message, data, status.value());
        return ResponseEntity.status(status).body(response);
    }



}
