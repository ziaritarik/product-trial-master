package com.althen.Ecommerce.web.dtos.auth;

import com.althen.Ecommerce.enums.AccountRole;
import com.althen.Ecommerce.web.dtos.AccountDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthLoginResponse {

    private AccountRole role;
    private String accessToken;
    private AccountDTO account;

    public AuthLoginResponse(String token, AccountDTO account) {
        this.accessToken = token;
        this.account = account;
        this.role = account.getAccountRole();
    }
}
