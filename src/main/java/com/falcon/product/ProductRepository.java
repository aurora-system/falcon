package com.falcon.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{

	Page<Product> findAllByCategoryId(long categoryId, Pageable page);
}
