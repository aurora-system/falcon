package com.falcon.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

	private CustomerRepository customerRepository;
	
	public CustomerController (CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	@GetMapping("/customers")
	public String listAllCustomers(Model model) {
		model.addAttribute("customers", customerRepository.findAll());
		model.addAttribute("message", "Hello from @GetMapping.");
		return "customer/customerList";
    }
}
