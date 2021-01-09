package com.falcon.common;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.falcon.product.Product;
import com.falcon.product.ProductRepository;
import com.falcon.salesorder.SalesOrder;
import com.falcon.salesorder.SalesOrderRepository;

@Controller
public class SearchController {

	private SalesOrderRepository orderRepository;
	private ProductRepository productRepository;
	
	public SearchController(SalesOrderRepository orderRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}
	
	@GetMapping("/search")
	public String dashboard(Model model, @RequestParam String keyword) {
	    
	    // Search in orders
	    List<SalesOrder> orderSearchResults = orderRepository.findAllByInvoiceNumberOrRemarks(keyword);
	    
	    // Search in products
	    List<Product> productSearchResults = productRepository.findByNameOrBrand(keyword);
	    
	    model.addAttribute("orderSearchResults", orderSearchResults);
	    model.addAttribute("productSearchResults", productSearchResults);
	    
	    return "common/searchresults";
	}
}
