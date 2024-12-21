package com.althen.Ecommerce.business.services;


import com.althen.Ecommerce.business.entities.Account;
import com.althen.Ecommerce.business.services.base.BaseService;
import io.micrometer.common.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IAccountService extends BaseService<Account>, UserDetailsService {

    Optional<Account> findByEmail(@NonNull String email);

    Account addAccount(@NonNull Account entity);
}
