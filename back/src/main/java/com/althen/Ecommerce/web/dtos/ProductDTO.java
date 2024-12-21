package com.althen.Ecommerce.web.dtos;

import com.althen.Ecommerce.web.dtos.base.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductDTO extends BaseDTO {
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
    private double rating;
}
