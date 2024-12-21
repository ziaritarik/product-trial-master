package com.althen.Ecommerce.business.entities;


import com.althen.Ecommerce.business.entities.base.UserDateAudit;
import com.althen.Ecommerce.enums.AccountRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name = "Utilisateurs", uniqueConstraints={ @UniqueConstraint(name = "UK_Utilisateurs_Email", columnNames={"Email"}) })
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Account extends UserDateAudit implements UserDetails {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Username")
    private String username;

    @Column(name = "Firstname")
    private String firstname;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Pssword", nullable = false)
    private String password;


    @Column(name = "Droit")
    @Enumerated(EnumType.STRING)
    private AccountRole accountRole = AccountRole.CLIENT;

    @Column(name = "Expiration", length = 1)
    private boolean credentialsExpired = true;

    @Column(name = "Actif", length = 1)
    private boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.accountRole.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}