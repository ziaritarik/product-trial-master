package com.althen.Ecommerce.business.repositories;

import com.althen.Ecommerce.business.entities.Account;
import com.althen.Ecommerce.business.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IAccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Optional<Account> findOneByUsername(String username);
    Optional<Account> findOneByEmail(String email);
}
