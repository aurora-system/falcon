package com.falcon.orders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {

	/**
	 * Displays the list of orders.
	 * 
	 * @param session
	 * @return orderListDto
	 */
	@GetMapping(value = {"/listDto"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody OrderListDto getOrderList(HttpSession session) {
		OrderListDto orderListDto = new OrderListDto();
		
		List<OrderDto> orderList = new ArrayList<OrderDto>();
		
		OrderDto dto1 = new OrderDto();
		dto1.setCustomerName("John Cruz");
		dto1.setMonthlyDueDate(3);
		dto1.setOrderId(1);
		dto1.setPaymentType("cash");
		dto1.setTotalAmount(new BigDecimal(100.0));
		dto1.setReferenceNum("000001");
		dto1.setType("service");
		dto1.setRemarks("This is a remark");
		dto1.setCreatedDate(new Date());
		orderList.add(dto1);
		
		orderListDto.setOrderList(orderList);
		return orderListDto;
	}
	
	@GetMapping("/orders")
    public String listOrders(Model model) {
        
		List<OrderDto> orderList = new ArrayList<OrderDto>();
		
		OrderDto dto1 = new OrderDto();
		dto1.setCustomerName("John Cruz");
		dto1.setMonthlyDueDate(3);
		dto1.setOrderId(1);
		dto1.setPaymentType("cash");
		dto1.setTotalAmount(new BigDecimal(100.0));
		dto1.setReferenceNum("000001");
		dto1.setType("service");
		dto1.setRemarks("This is a remark");
		dto1.setCreatedDate(new Date());
		orderList.add(dto1);
		
		model.addAttribute("orderList", orderList);
		model.addAttribute("message", "Hello from @GetMapping.");
		return "order/orderList";
    }
	
	@GetMapping("/orders/create")
    public String createOrder(Model model) {
        return "order/orderCreate";
    }
}
