package com.althen.Ecommerce.app.security.jwt;

import com.althen.Ecommerce.business.entities.Account;
import com.althen.Ecommerce.business.services.IAccountService;
import com.althen.Ecommerce.exceptions.JwtAuthenticationException;
import com.althen.Ecommerce.utils.HttpUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider tokenProvider;

    private IAccountService iAccountService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        log.info("doFilterInternal .. verifying token for {}.", request.getRequestURI());
        String jwt = HttpUtils.getJwtFromRequest(request);
        try {
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                String email = tokenProvider.getUsernameFromToken(jwt);
                Optional<Account> accountOpt = iAccountService.findByEmail(email);
                if (accountOpt.isPresent()) {
                    Account account = accountOpt.get();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new UsernameNotFoundException(String.format("User not found with id %s", email));
                }
            }
        } catch (Exception e) {
            log.error("Exception thrown while verifying token {}", e.getMessage());
            throw new JwtAuthenticationException("exceptions.not-authorized");
        }
        filterChain.doFilter(request, response);
    }
}