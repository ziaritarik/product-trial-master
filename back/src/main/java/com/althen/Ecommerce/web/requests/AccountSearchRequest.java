package com.althen.Ecommerce.web.requests;

import com.althen.Ecommerce.business.entities.Account;
import com.althen.Ecommerce.enums.AccountRole;
import com.althen.Ecommerce.web.dtos.base.BaseSearchRequest;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountSearchRequest extends BaseSearchRequest<Account> {

    private String firstname;
    private String username;
    private Boolean enabled;
    private AccountRole accountRole;


    @Override
    public Specification<Account> getSearchSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(this.getId())) {
                predicates.add(criteriaBuilder.equal(root.get("id"), this.getId()));            }

            if (StringUtils.hasText(this.getUsername())) {
                predicates.add(criteriaBuilder.equal(root.get("username"), this.getUsername()));
            }
            if (StringUtils.hasText(this.getFirstname())) {
                predicates.add(criteriaBuilder.equal(root.get("firstname"), this.getFirstname()));
            }
            if(Objects.nonNull(this.getAccountRole())) {
                predicates.add(criteriaBuilder.equal(root.get("accountRole"),this.getAccountRole()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
