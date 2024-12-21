package com.althen.Ecommerce.web.requests;

import com.althen.Ecommerce.business.entities.Product;
import com.althen.Ecommerce.web.dtos.base.BaseSearchRequest;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductRequest extends BaseSearchRequest<Product> {

    private Long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private BigDecimal price;
    private int quantity;
    private String internalReference;
    private String shellId;
    private String inventoryStatus;
    private double rating;;

    @Override
    public Specification<Product> getSearchSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(this.getId())) {
                predicates.add(criteriaBuilder.equal(root.get("id"), this.getId()));
            }
            if (Objects.nonNull(this.getCode())) {
                predicates.add(criteriaBuilder.equal(root.get("code"), this.getCode()));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

