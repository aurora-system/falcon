package com.falcon.service;

import java.util.List;

import com.falcon.entity.Product;

public interface ProductService {

	Product addProduct(Product product);
	List<Product> findAll(int page, int size, String sortBy);
}
