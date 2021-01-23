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
import org.springframework.web.bind.annotation.RequestParam;
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
		model.addAttribute(new SalesOrder());
		return "sales/orderlist";
	}
	
	@GetMapping("/salesorders/new")
	public String newSalesOrderForm(Model model) {
	    model.addAttribute(stockRepository.findAll());
		model.addAttribute(new SalesOrder());
		return "sales/orderform";
	}
	
	@GetMapping("/salesorders/{id}")
	public String viewSalesOrder(Model model 
			, @PathVariable long id) {
		model.addAttribute(salesOrderRepository.findById(id).orElseGet(() ->new SalesOrder()));
		model.addAttribute(stockRepository.findAll());
		return "sales/order";
	}
	
	@PostMapping("/salesorders/cancel")
    public String cancelSalesOrder(Model model
            , SalesOrder salesOrder
            , final RedirectAttributes redirect) {
        model.addAttribute(salesOrderRepository.findAll());
        SalesOrder order = salesOrderRepository.findById(salesOrder.getId()).get();
        order.setStatus("CANCELLED");
        
        String reason = salesOrder.getRemarks();
        if (reason == null || reason.equalsIgnoreCase("")) {
            reason = "Not specified.";
        }
        
        StringBuffer sb = new StringBuffer();
        sb.append(order.getRemarks());
        sb.append(" This order has been cancelled. Reason: " + reason);
        order.setRemarks(sb.toString());
        salesOrderRepository.save(order);
        
        redirect.addFlashAttribute("message", "Sales order cancelled successfully.");
        return "redirect:/salesorders";
    }
	
	@PostMapping("/salesorders")
	@Transactional
	public String saveSalesOrder(
			@Valid SalesOrder salesOrder
			, Errors errors
			, final RedirectAttributes redirect
			, Model model
			) {
		if (errors.hasErrors()) {
			// Populate model
		    return "sales/orderform";
		}
		BigDecimal totalAmount = salesOrder.getItems().stream()
				.map(SalesOrderItem::getNetSellingPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		salesOrder.setTotalAmount(totalAmount);
		
		// get items, remove those with null stock
		for (SalesOrderItem item : salesOrder.getItems()) {
		    if (item == null) {
		        salesOrder.getItems().remove(item);
		    }
		}
		
		salesOrderRepository.save(salesOrder);
		List<SalesOrderItem> items = salesOrder.getItems();
		for (SalesOrderItem soi : items) {
			// retrieve Stock, get product
		    Optional<Stock> oStock = stockRepository.findById(soi.getStock().getId());
		    Stock stock = oStock.get();
		    
		    oStock = stockRepository.findByProductIdAndUnitCost(stock.getProduct().getId()
					, stock.getUnitCost());
			if (oStock.isPresent()) {
				Stock s = oStock.get();
				s.setQuantity(s.getQuantity() - soi.getQuantity());
				stockRepository.save(s);
			} else {
				redirect.addFlashAttribute("message", "Stock is not found in system.");
				return "redirect:/salesorders";
			}
		}
		redirect.addFlashAttribute("message", "Sales order added successfully.");
		return "redirect:/salesorders";
	}
}
