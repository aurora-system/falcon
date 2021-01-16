package com.falcon.salesreturn;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.falcon.stock.StockRepository;

@Controller
public class SalesReturnController {

	private SalesReturnRepository salesReturnRepository;
	private StockRepository stockRepository;
	
	public SalesReturnController(
			SalesReturnRepository salesReturnRepository
			, StockRepository stockRepository
			) {
		
		this.salesReturnRepository = salesReturnRepository;
		this.stockRepository = stockRepository;
	}
	
	@GetMapping("/salesreturns")
	public String listSalesReturns(Model model) {
		model.addAttribute(salesReturnRepository.findAll());
		return "returns/salesreturnlist";
	}
	
	@GetMapping("/salesreturns/new")
	public String newSalesReturnForm(Model model) {
		model.addAttribute(stockRepository.findAll());
		model.addAttribute(new SalesReturn());
		return "returns/salesreturnform";
	}
	
	@GetMapping("/salesreturns/{id}/edit")
	public String editSalesReturnForm(Model model, @PathVariable long id) {
		model.addAttribute(stockRepository.findAll());
		model.addAttribute(salesReturnRepository.findById(id).orElseGet(() -> new SalesReturn()));
		return "returns/salesorderform";
	}
	
	@PostMapping("/salesreturns")
	@Transactional
	public String saveSalesReturn(
			@Valid SalesReturn salesReturn, Errors errors
			, final RedirectAttributes redirect
			, Model model
			) {
		if (errors.hasErrors()) {
			return "returns/salesorderform";
		}
		
		return "redirect:/salesreturns";
	}
}
