package com.falcon.orders;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

	@GetMapping("/orders")
    public String dashboard(Model model) {
        return "order/order";
    }
}
