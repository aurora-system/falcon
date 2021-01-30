package com.falcon.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{

    Page<Product> findAllByCategory(String category, Pageable page);

    @Query("SELECT DISTINCT(category) FROM Product")
    List<String> findAllCategories();

    @Query("select p from Product p where p.name like %:key% or p.brand like %:key%")
    List<Product> findByNameOrBrand(String key);
}
