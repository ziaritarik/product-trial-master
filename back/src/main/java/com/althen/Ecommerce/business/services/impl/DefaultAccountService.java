package com.althen.Ecommerce.business.services.impl;

import com.althen.Ecommerce.business.entities.Account;
import com.althen.Ecommerce.business.repositories.IAccountRepository;
import com.althen.Ecommerce.business.services.IAccountService;
import com.althen.Ecommerce.exceptions.ResourceNotFoundException;
import com.althen.Ecommerce.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Primary
@AllArgsConstructor
public class DefaultAccountService implements IAccountService {

    private final IAccountRepository iAccountRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with username :" + email));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findByEmail(@NonNull String email) {
        return iAccountRepository.findOneByEmail(email);
    }
    @Override
    @Transactional
    public Account addAccount(Account entity) {
        log.info("Adding new account for {}, with role of {}", entity.getUsername(), entity.getAccountRole());
        String password = CommonUtils.generatePassword(8);
        entity.setPassword(passwordEncoder.encode(password));
        return this.save(entity);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<Account> paginate(Specification<Account> specification, Pageable pageable) {
        return iAccountRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Long count(Specification<Account> specification) {
        return iAccountRepository.count(specification);
    }

    @Override
    @Transactional(readOnly = true)
    public Account findById(Long id) throws ResourceNotFoundException {
        return iAccountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("exception.account.not-found", id));
    }

    @Override
    @Transactional
    public Account save(Account entity) {
        return iAccountRepository.save(entity);
    }

    @Override
    @Transactional
    public Account update(Long id, Account entity) throws ResourceNotFoundException {
        Account current = this.findById(id);
        current.setUsername(entity.getUsername());
        current.setFirstname(entity.getFirstname());
        current.setEmail(entity.getEmail());

        return this.save(current);
    }

    @Override
    public boolean delete(Long id) throws ResourceNotFoundException, DataIntegrityViolationException {

        return false;
    }





}
