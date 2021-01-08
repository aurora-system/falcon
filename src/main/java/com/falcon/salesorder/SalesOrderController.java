package com.falcon.salesorder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
