package com.falcon.common;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.falcon.orders.Order;
import com.falcon.orders.OrderRepository;
import com.falcon.product.Product;
import com.falcon.product.ProductRepository;

@Controller
public class SearchController {

	private OrderRepository orderRepository;
	private ProductRepository productRepository;
	
	public SearchController(OrderRepository orderRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}
	
	@GetMapping("/search/{keyword}")
	public String dashboard(Model model, @PathVariable String keyword) {
	    
	    // Search in orders
	    List<Order> orderSearchResults = orderRepository.findByReferenceNumOrRemarks(keyword);
	    
	    // Search in products
	    List<Product> productSearchResults = productRepository.findByReferenceNumOrRemarks(keyword);
	    
	    model.addAttribute("orderSearchResults", orderSearchResults);
	    model.addAttribute("productSearchResults", productSearchResults);
	    
	    return "common/searchresults";
	}
}
