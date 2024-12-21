package com.althen.Ecommerce.business.services.base;


import com.althen.Ecommerce.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface BaseService<E>  {
    Page<E> paginate(Specification<E> specification, Pageable pageable);
    Long count(Specification<E> specification);
    E findById(Long id) throws ResourceNotFoundException;
    E save(E entity);
    E update(Long id, E entity) throws ResourceNotFoundException;
    boolean delete(Long id) throws ResourceNotFoundException, DataIntegrityViolationException;
}
