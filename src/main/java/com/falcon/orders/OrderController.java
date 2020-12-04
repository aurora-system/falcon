package com.falcon.orders;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.falcon.customer.CustomerRepository;
import com.falcon.product.ProductCategoryRepository;
import com.falcon.product.ProductRepository;

@Controller
public class OrderController {

    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private ProductCategoryRepository productCategoryRepository;

    public OrderController(OrderRepository orderRepository, ProductRepository productRepository,
	    ProductCategoryRepository productCategoryRepository, CustomerRepository customerRepository) {
	this.orderRepository = orderRepository;
	this.customerRepository = customerRepository;
	this.productRepository = productRepository;
	this.productCategoryRepository = productCategoryRepository;
    }

    @GetMapping("/orders")
    public String listAllOrders(Model model) {
	Iterable<Order> orders = orderRepository.findAll();
	model.addAttribute("orders", orders);
	return "order/orderlist";
    }

    @GetMapping({ "/orders/{orderId}" })
    public String orderDetails(@PathVariable long orderId, Model model) {
	Optional<Order> order = orderRepository.findById(orderId);
	Order o = order.orElse(new Order());
	model.addAttribute("order", o);
	return "order/order";
    }

    @GetMapping({"/orders/new"})
    public String newOrderForm(Model model) {
	OrderForm orderForm = new OrderForm();
	orderForm.getOrderItems().add(new OrderItem());
	model.addAttribute("orderForm", orderForm);
	model.addAttribute("customers", customerRepository.findAll());
	model.addAttribute("categories", productCategoryRepository.findAll());
	model.addAttribute("products", productRepository.findAll());
	return "order/orderform";
    }
}
