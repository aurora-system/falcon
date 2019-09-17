package com.falcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.falcon.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
