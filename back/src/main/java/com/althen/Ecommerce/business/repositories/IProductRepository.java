package com.althen.Ecommerce.business.repositories;

import com.althen.Ecommerce.business.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface IProductRepository  extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    }