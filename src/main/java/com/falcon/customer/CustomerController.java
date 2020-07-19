package com.falcon.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {
	
	@GetMapping("/customers")
    public String listExpenses(Model model) {
        
		List<CustomerDto> customerList = new ArrayList<CustomerDto>();
		
		CustomerDto dto1 = new CustomerDto();
		dto1.setName("Joe Torres");
		dto1.setAddress("100-A Admiral Village, San Pablo City");
		dto1.setContactNumber("09156689556");
		customerList.add(dto1);
		
		model.addAttribute("customerList", customerList);
		model.addAttribute("message", "Hello from @GetMapping.");
		return "customer/customerList";
    }
}
