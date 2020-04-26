package com.falcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.falcon.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	Page<Product> findAllByCategoryId(long categoryId, Pageable page);
}
