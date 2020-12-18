package com.falcon.report;

import java.time.LocalDate;
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

import com.falcon.orders.Order;
import com.falcon.orders.OrderRepository;
import com.falcon.product.Product;
import com.falcon.product.ProductCategory;
import com.falcon.product.ProductCategoryRepository;
import com.falcon.product.ProductRepository;

@Controller
public class ReportController {

	private ProductRepository productRepository;
	private ProductCategoryRepository productCategoryRepository;
	private OrderRepository orderRepository;

	public ReportController(
			ProductRepository productRepository
			,ProductCategoryRepository productCategoryRepository
			,OrderRepository orderRepository
			) {
		this.productRepository = productRepository;
		this.productCategoryRepository = productCategoryRepository;
		this.orderRepository = orderRepository;
	}
	
	@GetMapping({"/reports"})
	public String showReports(Model model) {
	    return "report/reports";
	}
	
	@GetMapping("/reports/orderstoday")
	public String listAllOrdersToday(Model model) {
	    Iterable<Order> orders = orderRepository.findByCreatedDate(LocalDate.now());
	    model.addAttribute("orders", orders);
	    return "report/orderstoday";
	}
}
