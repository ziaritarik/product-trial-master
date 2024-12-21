package com.althen.Ecommerce.web.mappers;

import com.althen.Ecommerce.business.entities.Product;
import com.althen.Ecommerce.web.dtos.ProductDTO;
import com.althen.Ecommerce.web.mappers.base.BaseMapper;
import com.althen.Ecommerce.web.mappers.base.BaseMapperConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        config = BaseMapperConfiguration.class)
public abstract class IProductMapper extends BaseMapper<Product, ProductDTO> {

    @Named("toEntity")
    public abstract Product mapToEntity(ProductDTO dto);

    @Named("toDTO")
    public abstract ProductDTO mapToDTO(Product entity);
}

