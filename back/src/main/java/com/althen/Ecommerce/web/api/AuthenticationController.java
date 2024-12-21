package com.althen.Ecommerce.web.api;

import com.althen.Ecommerce.app.annotations.ConnectedUser;
import com.althen.Ecommerce.app.security.jwt.JwtTokenProvider;
import com.althen.Ecommerce.business.entities.Account;
import com.althen.Ecommerce.business.services.IAccountService;
import com.althen.Ecommerce.enums.ApiResponseStatus;
import com.althen.Ecommerce.exceptions.ResourceNotFoundException;
import com.althen.Ecommerce.utils.EndPointsConstants;
import com.althen.Ecommerce.web.api._common.ApiValidationMessage;
import com.althen.Ecommerce.web.dtos.AccountDTO;
import com.althen.Ecommerce.web.dtos.auth.AuthLoginRequest;
import com.althen.Ecommerce.web.dtos.auth.AuthLoginResponse;
import com.althen.Ecommerce.web.dtos.base.ApiResponse;
import com.althen.Ecommerce.web.mappers.IAccountMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(EndPointsConstants.AUTHENTIFICATION_ENDPOINT)
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final IAccountService iAccountService;
    private final IAccountMapper iAccountMapper;
    private final MessageSource messageSource;

    @PostMapping("/signin")
    public ApiResponse<AuthLoginResponse> authenticateUser(@RequestBody AuthLoginRequest request, final WebRequest webRequest) {
        List<String> messages = isValidLoginRequest(request, webRequest);
        if(messages.isEmpty()){
            Optional<Account> accountOpt = iAccountService.findByEmail(request.getEmail());
            if(accountOpt.isPresent()){
                Account account = accountOpt.get();
                if(account.isEnabled()) {
                    Authentication authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(account, request.getPassword() )
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String token = tokenProvider.generateToken(authentication);
                    return ApiResponse.ok(messageSource.getMessage("auth.access.success", null, webRequest.getLocale()), new AuthLoginResponse(token, iAccountMapper.mapToDTO(account)));
                } else {
                    return ApiResponse.ko(ApiResponseStatus.RESTRICTED, messageSource.getMessage("auth.access.disabled", null, webRequest.getLocale()));
                }
            }else{
                throw new ResourceNotFoundException("auth.access.failed", request.getEmail());
            }
        }
        return ApiResponse.ko(ApiResponseStatus.VALIDATION, "validate", messages);
    }

    @GetMapping("/check-in")
    public ApiResponse<AccountDTO> checkIn(final WebRequest webRequest, @ConnectedUser Account account) {
        if(Objects.nonNull(account)){
            return ApiResponse.ok(iAccountMapper.mapToDTO(account));
        }
        return ApiResponse.ko(ApiResponseStatus.RESTRICTED, messageSource.getMessage("auth.access.required",null,  webRequest.getLocale()));
    }

    private List<String>  isValidLoginRequest(AuthLoginRequest request, WebRequest webRequest) {
        List<String> errors = new ArrayList<>();
        if(!StringUtils.hasText(request.getEmail())){
            errors.add(ApiValidationMessage.UserNameIsRequired);
        }
        if(!StringUtils.hasText(request.getPassword())){
            errors.add(ApiValidationMessage.PasswordIsRequired);
        }
        return errors;
    }
}
