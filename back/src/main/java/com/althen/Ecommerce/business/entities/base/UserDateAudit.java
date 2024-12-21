package com.althen.Ecommerce.business.entities.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;



@Data
@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "updatedBy"},
        allowGetters = true
)
@EqualsAndHashCode(callSuper = false)
public abstract class UserDateAudit extends DateAudit {

    @CreatedBy
    private Long createdBy;

    @CreatedBy
    private Long updatedBy;

}
