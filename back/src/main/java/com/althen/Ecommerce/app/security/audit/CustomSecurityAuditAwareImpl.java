package com.althen.Ecommerce.app.security.audit;

import com.althen.Ecommerce.business.entities.Account;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

public class CustomSecurityAuditAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || !authentication.isAuthenticated() ||  authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        Account account = (Account) authentication.getPrincipal();
        return Optional.ofNullable(account.getId());
    }
}