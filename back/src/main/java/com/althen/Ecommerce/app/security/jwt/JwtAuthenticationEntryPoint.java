package com.althen.Ecommerce.app.security.jwt;

import com.althen.Ecommerce.enums.ApiResponseStatus;
import com.althen.Ecommerce.web.dtos.base.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
        log.error("Trying unauthorized access and responding with error message: {}", e.getMessage());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(httpServletResponse.getOutputStream(), ApiResponse.ko(ApiResponseStatus.RESTRICTED, messageSource.getMessage("auth.unauthorized", new Object[]{}, httpServletRequest.getLocale())).toMap());
        httpServletResponse.getOutputStream().flush();
    }
}
