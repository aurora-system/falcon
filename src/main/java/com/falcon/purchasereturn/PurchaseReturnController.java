package com.falcon.purchasereturn;

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

import com.falcon.product.ProductRepository;
import com.falcon.stock.Stock;
import com.falcon.stock.StockRepository;
import com.falcon.supplier.SupplierRepository;

@Controller
public class PurchaseReturnController {

	private PurchaseReturnRepository purchaseReturnRepository;
	private ProductRepository productRepository;
	private SupplierRepository supplierRepository;
	private StockRepository stockRepository;
	
	public PurchaseReturnController(
			PurchaseReturnRepository purchaseReturnRepository
			, ProductRepository productRepository
			, SupplierRepository supplierRepository
			, StockRepository stockRepository
			) {
		this.purchaseReturnRepository = purchaseReturnRepository;
		this.productRepository = productRepository;
		this.supplierRepository = supplierRepository;
		this.stockRepository = stockRepository;
	}
	
	@GetMapping("/purchasereturns")
	public String listPurchaseReturns(Model model) {
		model.addAttribute(purchaseReturnRepository.findAll());
		return "returns/purchasereturnlist";
	}
	
	@GetMapping("/purchasereturns/new")
	public String newPurchaseReturnForm(Model model) {
		model.addAttribute(productRepository.findAll());
		model.addAttribute(supplierRepository.findAll());
		model.addAttribute(new PurchaseReturn());
		return "returns/purchasereturnform";
	}
	
	@GetMapping("/purchasereturns/{id}/edit")
	public String editPurchaseReturn(Model model, @PathVariable long id) {
		model.addAttribute(productRepository.findAll());
		model.addAttribute(supplierRepository.findAll());
		model.addAttribute(purchaseReturnRepository.findById(id).orElseGet(() -> new PurchaseReturn()));
		return "returns/purchasereturnform";
	}
	
	@PostMapping({"/purchasereturns"})
	@Transactional
	public String savePurchase(
			@Valid PurchaseReturn purchaseReturn, Errors errors
			, final RedirectAttributes redirect
			, Model model
			) {
		if (errors.hasErrors()) {
			model.addAttribute(productRepository.findAll());
			model.addAttribute(supplierRepository.findAll());
			return "returns/purchasereturnform";
		}
		purchaseReturnRepository.save(purchaseReturn);
		Optional<Stock> stock = stockRepository.findByProductIdAndUnitCost(purchaseReturn.getProduct().getId(), purchaseReturn.getUnitCost());
		if (stock.isPresent()) {
		    Stock s = stock.get();
		    s.setQuantity(s.getQuantity() - purchaseReturn.getQuantity());
		    stockRepository.save(s);
		} else {
			redirect.addFlashAttribute("message", "Stock is not found in system.");
			return "redirect:/purchasereturns";
		}
		redirect.addFlashAttribute("message", "New Purchase Return added successfully.");
		return "redirect:/purchasereturns";
	}
}
