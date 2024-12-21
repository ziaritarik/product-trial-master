package com.althen.Ecommerce.web.dtos.base;

import com.althen.Ecommerce.enums.ApiResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiPagedResponse<T> {

    private ApiResponseStatus status = ApiResponseStatus.OK;
    private String message;
    private Page<T> data;

    public static <T> ApiPagedResponse<T> ok(Page<T> data) {
        return new ApiPagedResponse<>(ApiResponseStatus.OK, null, data);
    }

    public static <T> ApiPagedResponse<T> ko(ApiResponseStatus status, String message) {
        return new ApiPagedResponse<>(status, message, null);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        if(this.status == ApiResponseStatus.OK) {
            result.put("status", this.status);
            result.put("data", this.data);
        } else {
            result.put("status", this.status);
            result.put("message", this.message);
        }
        return result;
    }
}
