package com.althen.Ecommerce.web.dtos.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthLoginRequest {

    private String email;
    private String password;

}
