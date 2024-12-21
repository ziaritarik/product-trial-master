package com.althen.Ecommerce.web.dtos.base;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
public class BaseDTO extends UserDateAuditDTO {

}
