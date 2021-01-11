package com.falcon.salesorder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.falcon.stock.Stock;
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
	
	@GetMapping("/salesorders/{id}")
	public String viewSalesOrder(Model model, @PathVariable long id) {
		model.addAttribute(salesOrderRepository.findById(id).orElseGet(() ->new SalesOrder()));
		model.addAttribute(stockRepository.findAll());
		return "sales/order";
	}
	
	@PostMapping("/salesorders")
	@Transactional
	public String saveSalesOrder(
			@Valid SalesOrder salesOrder, Errors errors
			, final RedirectAttributes redirect
			, Model model
			) {
		if (errors.hasErrors()) {
			return "sales/orderform";
		}
		BigDecimal totalAmount = salesOrder.getItems().stream()
				.map(SalesOrderItem::getNetSellingPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		salesOrder.setTotalAmount(totalAmount);
		salesOrderRepository.save(salesOrder);
		List<SalesOrderItem> items = salesOrder.getItems();
		for (SalesOrderItem soi : items) {
			Optional<Stock> stock = stockRepository.findByProductIdAndUnitCost(soi.getStock().getProduct().getId()
					, soi.getStock().getUnitCost());
			if (stock.isPresent()) {
				Stock s = stock.get();
				s.setQuantity(s.getQuantity() - soi.getQuantity());
				stockRepository.save(s);
			} else {
				redirect.addFlashAttribute("message", "Stock is not found in system.");
				return "redirect:/salesorders";
			}
		}
		return "redirect:/salesorders";
	}
}
