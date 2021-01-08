package com.falcon.stock;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.falcon.product.ProductRepository;
import com.falcon.purchase.PurchaseRepository;
import com.falcon.supplier.SupplierRepository;

@Controller
public class StockController {
	
	private StockRepository stockRepository;
	private PurchaseRepository purchaseRepository;
	private ProductRepository productRepository;
	private SupplierRepository supplierRepository;
	
	public StockController(
			StockRepository stockRepository
			, PurchaseRepository purchaseRepository
			, ProductRepository productRepository
			, SupplierRepository supplierRepository
			) {
		this.stockRepository = stockRepository;
		this.purchaseRepository = purchaseRepository;
		this.productRepository = productRepository;
		this.supplierRepository = supplierRepository;
	}
	
	@GetMapping("/stocks")
	public String availableStocks(Model model) {
		
		model.addAttribute(stockRepository.findAll());
		return "stocks/stocklist";
	}
	
	@GetMapping("/stocks/{id}/edit")
	public String updateMarkupForm(Model model, @PathVariable long id) {
		model.addAttribute(stockRepository.findById(id).orElseGet(() -> new Stock()));
		model.addAttribute(stockRepository.findAll());
		return "stocks/stockform";
	}
	
	@PostMapping("/stocks")
	public String updateMarkup(
			@Valid Stock stock, Errors errors
			, final RedirectAttributes redirect
			, Model model
			) {
		if (errors.hasErrors()) {
			return "stocks/stocklist";
		}
		stockRepository.save(stock);
		return "redirect:/stocks";
	}
}
