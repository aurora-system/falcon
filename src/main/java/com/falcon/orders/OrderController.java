package com.falcon.orders;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.falcon.product.ProductRepository;

@Controller
public class OrderController {

	private OrderRepository orderRepository;
	private ProductRepository productRepository;
	
	public OrderController(OrderRepository orderRepository, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}
	/**
	 * Displays the list of orders.
	 * 
	 * @param session
	 * @return orderListDto
	 */
//	@GetMapping(value = {"/listDto"}, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody OrderListDto getOrderList(HttpSession session) {
//		
//		OrderListDto orderListDto = new OrderListDto();
//		
//		Iterable<Order> orderList = orderRepository.findAll();
//		
//		return orderListDto;
//	}
	
	@GetMapping("/orders")
    public String listAllOrders(Model model) {
		Iterable<Order> orders = orderRepository.findAll();
		model.addAttribute("orders", orders);
		return "order/orderlist";
    }
	
	@GetMapping({"/orders/{orderId}"})
	public String orderDetails(@PathVariable long orderId, Model model) {
		Optional<Order> order = orderRepository.findById(orderId);
		Order o = order.orElse(new Order());
		model.addAttribute("order", o);
		return "order/order";
	}
	
	@GetMapping({"/orders/new"})
	public String newOrderForm(Model model) {
		model.addAttribute("order", new Order());
		model.addAttribute("products", productRepository.findAll());
		return "order/orderform";
	}
}
