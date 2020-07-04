package com.falcon.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.falcon.entity.ProductCategory;

public interface ProductCategoryRepository extends PagingAndSortingRepository<ProductCategory, Long> {

}
