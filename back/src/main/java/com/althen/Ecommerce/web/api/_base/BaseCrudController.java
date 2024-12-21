package com.althen.Ecommerce.web.api._base;

import com.althen.Ecommerce.app.annotations.ConnectedUser;
import com.althen.Ecommerce.business.entities.Account;
import com.althen.Ecommerce.web.dtos.base.ApiPagedResponse;
import com.althen.Ecommerce.web.dtos.base.ApiResponse;
import com.althen.Ecommerce.web.dtos.base.BaseDTO;
import com.althen.Ecommerce.web.dtos.base.BaseSearchRequest;

import java.util.List;
import java.util.Map;

public interface BaseCrudController <M, D extends BaseDTO, S extends BaseSearchRequest<M>> {

    ApiPagedResponse<D> paginate(S searchRequest, @ConnectedUser Account account);

    ApiResponse<D> find(Long id,  @ConnectedUser Account account);

    ApiResponse<D> find(String code,  @ConnectedUser Account account);

    ApiResponse<D> save(D form, @ConnectedUser Account account);

    ApiResponse<D> update(Long id, D form, @ConnectedUser Account account);

    ApiResponse<D> patch(Long id, Map<String, Object> form, @ConnectedUser Account account);

    ApiResponse<Boolean> delete(Long id, @ConnectedUser Account account);

}
