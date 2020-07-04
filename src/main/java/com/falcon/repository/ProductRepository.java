package com.falcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.falcon.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{

	Page<Product> findAllByProductCategoryCategoryId(long categoryId, Pageable page);
}
