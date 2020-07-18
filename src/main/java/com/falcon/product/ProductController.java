package com.falcon.product;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

	private ProductRepository productRepository;
	private ProductCategoryRepository productCategoryRepository;

	public ProductController(
			ProductRepository productRepository
			,ProductCategoryRepository productCategoryRepository
			) {
		this.productRepository = productRepository;
		this.productCategoryRepository = productCategoryRepository;
	}
	
	@GetMapping({"/productcategories"})
	public String listAllProductCategories(Model model) {
		Iterable<ProductCategory> categories = productCategoryRepository.findAll();
		model.addAttribute("categories", categories);
		return "product/categorylist";
	}
	
	@GetMapping({"/productcategories/{categoryId}"})
	public String categoryDetails(@PathVariable long categoryId, Model model) {
		ProductCategory cat = productCategoryRepository.findById(categoryId)
				.orElse(new ProductCategory(""));
		model.addAttribute("category", cat);
		return "product/category";
	}
	
	@GetMapping({"/products"})
	public String listAllProducts(Model model) {
		Iterable<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
		return "product/productlist";
	}
	
	@GetMapping({"/products/category/{categoryId}"})
	public String listAllProductsByCategory(@PathVariable long categoryId, Model model) {
		Pageable pageOne = PageRequest.of(0, 20);
		Iterable<Product> products = productRepository.findAllByProductCategoryId(categoryId, pageOne);
		model.addAttribute("products", products);
		return "product/productlist";
	}
	
	@GetMapping({"/products/{productId}"})
	public String productDetails(@PathVariable long productId, Model model) {
		Optional<Product> product = productRepository.findById(productId);
		Product p = product.orElse(new Product());
		model.addAttribute("product", p);
		return "product/product";
	}
}
