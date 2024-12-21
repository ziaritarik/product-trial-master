package com.althen.Ecommerce.web.api.product;

import com.althen.Ecommerce.app.annotations.ConnectedUser;
import com.althen.Ecommerce.business.entities.Account;
import com.althen.Ecommerce.business.entities.Product;
import com.althen.Ecommerce.business.services.IProductService;
import com.althen.Ecommerce.enums.AccountRole;
import com.althen.Ecommerce.enums.ApiResponseStatus;
import com.althen.Ecommerce.utils.EndPointsConstants;
import com.althen.Ecommerce.web.api._base.BaseCrudController;
import com.althen.Ecommerce.web.dtos.ProductDTO;
import com.althen.Ecommerce.web.dtos.base.ApiPagedResponse;
import com.althen.Ecommerce.web.dtos.base.ApiResponse;
import com.althen.Ecommerce.web.mappers.IProductMapper;
import com.althen.Ecommerce.web.requests.ProductRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(EndPointsConstants.TRAITEMENT_PRODUCT_ENDPOINT)
public class ProductController implements BaseCrudController<Product, ProductDTO, ProductRequest> {

    private final IProductService iService;
    private final IProductMapper iMapper;

    //GET Retrieve all products
    @Override
    @GetMapping("/products")
    public ApiPagedResponse<ProductDTO> paginate(ProductRequest searchRequest, Account account) {
        Page<Product> page = iService.paginate(searchRequest.getSearchSpecification(), searchRequest.getPageable());
        return ApiPagedResponse.ok(page.map(iMapper::mapToDTO));
    }

    //GET Retrieve details for product 1
    @GetMapping("/products/{id}")
    public ApiResponse<ProductDTO> findById(@PathVariable Long id,@ConnectedUser Account account) {
        Product entity = iService.findById(id);
        return ApiResponse.ok(iMapper.mapToDTO(entity));
    }


    @Override
    public ApiResponse<ProductDTO> find(Long id, Account account) {
        return null;
    }

    @Override
    public ApiResponse<ProductDTO> find(String code, Account account) {
        return null;
    }

    //POST Create a new product
    @Override
    public ApiResponse<ProductDTO> save(@RequestBody ProductDTO form, @ConnectedUser Account account) {
        if(!account.getAccountRole().equals(AccountRole.ADMIN)){
            throw new AuthorizationServiceException("exceptions.not-authorized");
        }
        Product entity = iMapper.mapToEntity(form);
        entity = iService.save(entity);
        return ApiResponse.ok(iMapper.mapToDTO(entity));
    }

    @Override
    public ApiResponse<ProductDTO> update(Long id, ProductDTO form, Account account) {
        Product entity = iMapper.mapToEntity(form);
        // Check if the account role is ADMIN
        if (account.getAccountRole().equals(AccountRole.ADMIN)) {
            entity = iService.update(id, entity);
            return ApiResponse.ok(iMapper.mapToDTO(entity));
        } else {
            // If the role is not ADMIN, return an error response
            return ApiResponse.ko(ApiResponseStatus.VALIDATION, "Merci de vérifier les erreurs!");
        }
    }



    @Override
    public ApiResponse<ProductDTO> patch(Long id, Map<String, Object> form, Account account) {
        if(!account.getAccountRole().equals(AccountRole.ADMIN)){
            throw new AuthorizationServiceException("exceptions.not-authorized");
        }
        Product existingProduct = iService.findById(id);
        if (existingProduct == null) {
            return ApiResponse.ko(ApiResponseStatus.NOT_FOUND, "Product not found.");
        }

        if (account.getAccountRole().equals(AccountRole.ADMIN)) {
            Product entity = iMapper.mapToEntity((ProductDTO) form);
            entity.setId(id);
            entity = iService.update(id, entity);
            return ApiResponse.ok(iMapper.mapToDTO(entity));
        } else {
            // If the role is not ADMIN, return an error response
            return ApiResponse.ko(ApiResponseStatus.VALIDATION, "Merci de vérifier les erreurs!");
        }
    }


    @Override
    public ApiResponse<Boolean> delete(@PathVariable Long id, @ConnectedUser Account account) {
        // Check if the account has the ADMIN role
        if (account.getAccountRole().equals(AccountRole.ADMIN)) {
            Product existingProduct = iService.findById(id);
            if (existingProduct == null) {
                return ApiResponse.ko(ApiResponseStatus.NOT_FOUND, "Product not found.");
            }

            boolean deleted = iService.delete(id);

            if (deleted) {
                return ApiResponse.ok(true);
            } else {
                return ApiResponse.ko(ApiResponseStatus.INTERNAL_ERROR, "Error deleting the product.");
            }
        } else {
            return ApiResponse.ko(ApiResponseStatus.VALIDATION, "Permission denied: Only ADMIN can delete products.");
        }
    }


}
