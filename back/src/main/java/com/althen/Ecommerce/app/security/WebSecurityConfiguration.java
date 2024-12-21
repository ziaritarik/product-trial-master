package com.althen.Ecommerce.app.security;

import com.althen.Ecommerce.app.properties.SecurityTokenProperties;
import com.althen.Ecommerce.app.security.jwt.JwtAuthenticationEntryPoint;
import com.althen.Ecommerce.app.security.jwt.JwtAuthenticationFilter;
import com.althen.Ecommerce.app.security.jwt.JwtTokenProvider;
import com.althen.Ecommerce.business.services.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfiguration {

    private IAccountService iAccountService;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private PasswordEncoder passwordEncoder;
    private SecurityTokenProperties tokenProperties;


    @Bean
    public UserDetailsService userDetailsService() {
        return this.iAccountService;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(new JwtTokenProvider(tokenProperties), iAccountService);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChains(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**");
        http.authorizeHttpRequests(
                (authorize) ->
                        authorize.requestMatchers("/api/auth/**").permitAll()
                                .anyRequest().fullyAuthenticated()
        );

        // -- Sessions
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // -- Exceptions
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(this.jwtAuthenticationEntryPoint));

        // -- Authentication provider
        http.authenticationProvider(authenticationProvider());

        // -- JWT Security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
