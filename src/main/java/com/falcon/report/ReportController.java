package com.falcon.report;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.falcon.product.Product;
import com.falcon.product.ProductCategory;
import com.falcon.product.ProductCategoryRepository;
import com.falcon.product.ProductRepository;

@Controller
public class ReportController {

	private ProductRepository productRepository;
	private ProductCategoryRepository productCategoryRepository;

	public ReportController(
			ProductRepository productRepository
			,ProductCategoryRepository productCategoryRepository
			) {
		this.productRepository = productRepository;
		this.productCategoryRepository = productCategoryRepository;
	}
	
	@GetMapping({"/reports"})
	public String showReports(Model model) {
		return "report/reports";
	}
}
