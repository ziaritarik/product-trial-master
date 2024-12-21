package com.althen.Ecommerce.app.security.jwt;

import com.althen.Ecommerce.app.properties.SecurityTokenProperties;
import com.althen.Ecommerce.business.entities.Account;
import com.althen.Ecommerce.exceptions.JwtAuthenticationException;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;


@Slf4j
@Component
@AllArgsConstructor
public class JwtTokenProvider {

    private SecurityTokenProperties tokenProperties;

    public String generateToken(Authentication authentication) {
        log.info("Building auth token for {}", authentication.getName());
        Account account = (Account) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + this.tokenProperties.getExpiration());

        return Jwts.builder()
                .setSubject(account.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, this.tokenProperties.getSecret())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        log.info("Fetching user id from token {}", token);
        Claims claims = Jwts.parser()
                .setSigningKey(this.tokenProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) throws JwtAuthenticationException {
        log.info("Validating auth token {}", authToken);
        try {
            Jwts.parser().setSigningKey(this.tokenProperties.getSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw new JwtAuthenticationException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new JwtAuthenticationException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new JwtAuthenticationException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new JwtAuthenticationException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw new JwtAuthenticationException("JWT claims string is empty.");
        }
    }

    public boolean tokenExpired(String authToken) {
        try {
            return !this.validateToken(authToken);
        } catch (JwtAuthenticationException e) {
            return false;
        }
    }
}
