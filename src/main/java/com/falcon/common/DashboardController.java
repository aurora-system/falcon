package com.falcon.common;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.falcon.orders.Order;
import com.falcon.orders.OrderItem;
import com.falcon.orders.OrderRepository;
import com.falcon.product.ProductRepository;

@Controller
public class DashboardController {

	private OrderRepository orderRepository;
	private ProductRepository productRepository;
	
	public DashboardController(OrderRepository orderRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		
		// Weekly Sales Data
		Map<String, Integer> weeklySalesData = new LinkedHashMap<String, Integer>();
		weeklySalesData.put("9/20", 57500);
		weeklySalesData.put("9/21", 20400);
		weeklySalesData.put("9/22", 30500);
		weeklySalesData.put("9/23", 35760);
		weeklySalesData.put("9/24", 14211);
		weeklySalesData.put("9/25", 8950);
		weeklySalesData.put("9/26", 88300);
		
		// Daily Product Sales
		Map<String, Integer> dailyProductSalesData = new LinkedHashMap<String, Integer>();
		dailyProductSalesData.put("Toyota Corolla bumper", 5);
		dailyProductSalesData.put("Honda Civic windshield", 2);
		dailyProductSalesData.put("Clifford's Diffuser", 3);
		dailyProductSalesData.put("Vossen VIP Rims", 8);
		dailyProductSalesData.put("Wild Trak Exhaust", 3);
		
		model.addAttribute("weeklySalesData", weeklySalesData);
	        model.addAttribute("dailyProductSalesData", dailyProductSalesData);
		model.addAttribute("message", "Hello from @GetMapping.");
		
		List<Order> ordersToday = orderRepository.findByCreatedDate(LocalDate.now());
		List<OrderItem> orderItems = ordersToday.stream()
				.map(Order::getOrderItems)
				.flatMap(List::stream)
				.collect(toList());
		Map<Object, Long> ordersTodayMap = orderItems.stream()
				.collect(Collectors.groupingBy(item -> item.getProduct().getName()
						, counting()));
		
		model.addAttribute("dailyProductSales", ordersTodayMap);
		return "common/dashboard";
	}
}
