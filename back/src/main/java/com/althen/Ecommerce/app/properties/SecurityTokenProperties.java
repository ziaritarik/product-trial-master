package com.althen.Ecommerce.app.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.jwt")
public class SecurityTokenProperties {

    private String secret;
    private int expiration;

}