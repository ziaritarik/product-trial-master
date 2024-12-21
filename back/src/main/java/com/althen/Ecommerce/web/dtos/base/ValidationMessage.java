package com.althen.Ecommerce.web.dtos.base;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationMessage {

    private String field;
    private String message;
}
