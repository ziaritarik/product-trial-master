package com.althen.Ecommerce.web.validators;

import com.althen.Ecommerce.business.services.IAccountService;
import com.althen.Ecommerce.utils.CommonUtils;
import com.althen.Ecommerce.web.api._common.ApiValidationMessage;
import com.althen.Ecommerce.web.dtos.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class AccountValidator {
    private final IAccountService iService;
    public List<String> isValid(@NonNull AccountDTO dto){
        List<String> messages = new ArrayList<>();
        if(Objects.isNull(dto.getAccountRole())){
            messages.add(ApiValidationMessage.AccountRoleIsRequired);
        }

        if(!StringUtils.hasText(dto.getUsername())) {
            messages.add(ApiValidationMessage.UserNameIsRequired);
        } else if(iService.findByEmail(dto.getEmail()).isPresent()){
            messages.add(ApiValidationMessage.UserNameIsAlreadyExists);
        }
        if(!StringUtils.hasText(dto.getEmail())){
            messages.add(ApiValidationMessage.EmailIsRequired);
        } else if (!CommonUtils.isValidEmail(dto.getEmail())) {
            messages.add(ApiValidationMessage.EmailIsNotValid);
        }
        return messages;
    }


}
