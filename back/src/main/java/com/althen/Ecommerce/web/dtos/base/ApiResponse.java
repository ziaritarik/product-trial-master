package com.althen.Ecommerce.web.dtos.base;

import com.althen.Ecommerce.enums.ApiResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private ApiResponseStatus status = ApiResponseStatus.OK;
    private String message;
    private T data;
    private List<String> errors;


    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(ApiResponseStatus.OK, null, data, null);
    }
    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(ApiResponseStatus.OK, message, data, null);
    }

    public static <T> ApiResponse<T> ko(ApiResponseStatus status, String message) {
        return new ApiResponse<>(status, message, null, null);
    }

    public static <T> ApiResponse<T> ko(ApiResponseStatus status, String message, List<String> errors) {
        return new ApiResponse<>(status, message, null, errors);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", this.status);
        result.put("data", this.data);
        result.put("message", this.message);
        result.put("errors", this.errors);
        return result;
    }
}
