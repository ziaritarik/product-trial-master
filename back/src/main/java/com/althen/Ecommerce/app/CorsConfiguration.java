package com.althen.Ecommerce.app;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Configuration
public class CorsConfiguration {
	
	private static final List<String> URL_FRONT_END = Arrays.asList(
		"http://localhost:4200","http://localhost:4300"
	);

	@Bean
	public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.setAllowedOrigins(URL_FRONT_END);
	    config.setAllowedMethods(Collections.singletonList("*"));
	    config.setAllowedHeaders(Collections.singletonList("*"));
	    source.registerCorsConfiguration("/**", config);
	    FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
	    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
	    return bean;
	}

}
