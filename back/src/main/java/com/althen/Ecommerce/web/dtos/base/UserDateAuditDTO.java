package com.althen.Ecommerce.web.dtos.base;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDateAuditDTO {

    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
