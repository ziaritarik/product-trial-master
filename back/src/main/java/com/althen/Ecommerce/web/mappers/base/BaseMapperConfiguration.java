package com.althen.Ecommerce.web.mappers.base;

import org.mapstruct.Builder;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingInheritanceStrategy;

@MapperConfig(
        componentModel = "spring",
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG,
        builder = @Builder(disableBuilder = true)
)
public interface BaseMapperConfiguration {}
