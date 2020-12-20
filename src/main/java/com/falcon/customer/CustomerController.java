package com.falcon.customer;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.falcon.expense.Expense;

@Controller
public class CustomerController {

    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
	this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public String listAllCustomers(Model model) {
	model.addAttribute("customers", customerRepository.findAll());
	model.addAttribute("message", "Hello from @GetMapping.");
	return "customer/customerlist";
    }
    
    @GetMapping({"/customers/new"})
    public String newCustomerForm(Model model) {
	model.addAttribute("customerForm", new Customer());
	return "customer/customerform";
    }
    
    @PostMapping("/customers")
    public String saveCustomer(@Valid Customer customer, Errors errors, final RedirectAttributes redirect) {
	
	if (errors.hasErrors()) {
		return "customer/customerform";
	}
	
	customerRepository.save(customer);
	return "redirect:/customers";
    }
}
