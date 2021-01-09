package com.falcon.salesorder;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.falcon.stock.StockRepository;

@Controller
public class SalesOrderController {

	private SalesOrderRepository salesOrderRepository;
	private StockRepository stockRepository;
	
	public SalesOrderController(
			SalesOrderRepository salesOrderRepository
			, StockRepository stockRepository
			) {
		this.salesOrderRepository = salesOrderRepository;
		this.stockRepository = stockRepository;
	}
	
	@GetMapping("/salesorders")
	public String listSalesOrders(Model model) {
		model.addAttribute(salesOrderRepository.findAll());
		return "sales/orderlist";
	}
	
	@GetMapping("/salesorders/new")
	public String newSalesOrderForm(Model model) {
		model.addAttribute(stockRepository.findAll());
		model.addAttribute(new SalesOrder());
		return "sales/orderform";
	}
	
	@PostMapping("/salesorders")
	public String saveSalesOrder(
			@Valid SalesOrder salesOrder, Errors errors
			, final RedirectAttributes redirect
			, Model model
			) {
		if (errors.hasErrors()) {
			return "sales/orderform";
		}
		BigDecimal totalAmount = salesOrder.getSalesOrderItems().stream()
				.map(SalesOrderItem::getNetSellingPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		salesOrder.setTotalAmount(totalAmount);
		salesOrderRepository.save(salesOrder);
		return "redirect:/salesorders";
	}
}
