package com.falcon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.falcon.dto.AddProductRequest;
import com.falcon.entity.Product;
import com.falcon.service.ProductService;

@Controller
public class MainController {
	
	@Autowired
	ProductService productService;
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/products")
	public @ResponseBody List<Product> findAll(@RequestParam int page) {
		return productService.findAll(page-1, 10, "name");
	}
	
	@PostMapping("/products")
	public @ResponseBody Product addProduct(@RequestBody AddProductRequest addProductRequest) {
		Product product = new Product(
				addProductRequest.getName(),
				addProductRequest.getBrand(),
				addProductRequest.getModel(),
				addProductRequest.getPrice()
				);
		productService.addProduct(product);
		return product;
	}
}
