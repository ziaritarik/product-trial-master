package com.althen.Ecommerce.business.services.impl;

import com.althen.Ecommerce.business.entities.Product;
import com.althen.Ecommerce.business.repositories.IProductRepository;
import com.althen.Ecommerce.business.services.IProductService;
import com.althen.Ecommerce.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DefaultProductService implements IProductService {

    private final IProductRepository iRepository;
    public DefaultProductService() {
        this.iRepository = null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> paginate(Specification<Product> specification, Pageable pageable) {
        return iRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Long count(Specification<Product> specification) {
        return iRepository.count(specification);
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(Long id) throws ResourceNotFoundException {
        return iRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("exception.promotion.not-found", id));
    }

    @Override
    @Transactional
    public Product save(Product entity) {
        return iRepository.save(entity);
    }

    @Override
    @Transactional
    public Product update(Long id, Product entity) throws ResourceNotFoundException {
        Product current = this.findById(id);
        return this.save(current);
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws ResourceNotFoundException, DataIntegrityViolationException {
        Product current = this.findById(id);
        iRepository.delete(current);
        return false;
    }





}
