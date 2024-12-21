package com.althen.Ecommerce.web.dtos;

import com.althen.Ecommerce.enums.AccountRole;
import com.althen.Ecommerce.web.dtos.base.BaseDTO;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountDTO extends BaseDTO {

    private Long id;
    private String username;
    private String firstname;
    private String email;
    private String password;
    private AccountRole accountRole = AccountRole.CLIENT;

}
