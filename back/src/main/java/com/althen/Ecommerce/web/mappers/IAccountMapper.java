package com.althen.Ecommerce.web.mappers;

import com.althen.Ecommerce.business.entities.Account;
import com.althen.Ecommerce.web.dtos.AccountDTO;
import com.althen.Ecommerce.web.mappers.base.BaseMapper;
import com.althen.Ecommerce.web.mappers.base.BaseMapperConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Slf4j
@Mapper(componentModel = "spring",
        uses = {
                IProductMapper.class
        },
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        config = BaseMapperConfiguration.class
)
public abstract class IAccountMapper extends BaseMapper<Account, AccountDTO> {

    @Override
    @Named("toEntity")
    public abstract Account mapToEntity(AccountDTO dto);

    @Override
    @Named("toDTO")
    public abstract AccountDTO mapToDTO(Account entity);

}


