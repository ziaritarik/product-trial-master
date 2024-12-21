package com.althen.Ecommerce.web.api;

import com.althen.Ecommerce.app.annotations.ConnectedUser;
import com.althen.Ecommerce.business.entities.Account;
import com.althen.Ecommerce.business.services.IAccountService;
import com.althen.Ecommerce.enums.ApiResponseStatus;
import com.althen.Ecommerce.exceptions.ResourceNotFoundException;
import com.althen.Ecommerce.utils.EndPointsConstants;
import com.althen.Ecommerce.web.api._base.BaseCrudController;
import com.althen.Ecommerce.web.dtos.AccountDTO;
import com.althen.Ecommerce.web.dtos.base.ApiPagedResponse;
import com.althen.Ecommerce.web.dtos.base.ApiResponse;
import com.althen.Ecommerce.web.mappers.IAccountMapper;
import com.althen.Ecommerce.web.requests.AccountSearchRequest;
import com.althen.Ecommerce.web.validators.AccountValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(EndPointsConstants.ACCOUNT_ENDPOINT)
public class AccountController implements BaseCrudController<Account, AccountDTO, AccountSearchRequest> {

    private final IAccountService iService;
    private final IAccountMapper iMapper;
    private final AccountValidator validator;


    @GetMapping("/")
    public ApiPagedResponse<AccountDTO> paginate(AccountSearchRequest searchRequest, @ConnectedUser Account account) {
        Page<Account> page = iService.paginate(searchRequest.getSearchSpecification(), searchRequest.getPageable());
        return ApiPagedResponse.ok(page.map(iMapper::mapToDTO));
    }

    @PostMapping("/register")
    public ApiResponse<AccountDTO> register(@RequestBody AccountDTO form, @ConnectedUser Account account) {
        List<String> errors = validator.isValid(form);
        if(errors.isEmpty()){
            Account entity = iMapper.mapToEntity(form);
            entity = iService.addAccount(entity);
            return ApiResponse.ok(iMapper.mapToDTO(entity));
        }
        return ApiResponse.ko(ApiResponseStatus.VALIDATION, "Formulaire invalide", errors);
    }


    @GetMapping("/{id}")
    public ApiResponse<AccountDTO> find(@PathVariable Long id, @ConnectedUser Account account) {
        return ApiResponse.ok(iMapper.mapToDTO(iService.findById(id)));
    }

    @GetMapping("/username/{username}")
    public ApiResponse<AccountDTO> find(@PathVariable String username,@ConnectedUser Account account) {
        return ApiResponse.ok(iMapper.mapToDTO(iService.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("exception.account.not-found", username))));
    }

    @PostMapping("/")
    public ApiResponse<AccountDTO> save(@RequestBody AccountDTO form,@ConnectedUser Account account) {
        return ApiResponse.ko(ApiResponseStatus.RESTRICTED, "Action non autorisé.");
    }


    @PutMapping("/{id}")
    public ApiResponse<AccountDTO> update(@PathVariable Long id,  @RequestBody AccountDTO form,@ConnectedUser Account account) {
        Account entity = iMapper.mapToEntity(form);
        entity = iService.update(id, entity);
        return ApiResponse.ok("Modification du compte terminer avec succès", iMapper.mapToDTO(entity));
    }

    @Override
    public ApiResponse<AccountDTO> patch(Long id, Map<String, Object> form, Account account) {
        return null;
    }

    @Override
    public ApiResponse<Boolean> delete(Long id, Account account) {
        return null;
    }


}
