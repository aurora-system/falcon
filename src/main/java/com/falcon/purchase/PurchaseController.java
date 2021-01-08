package com.falcon.purchase;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.falcon.product.ProductRepository;
import com.falcon.supplier.SupplierRepository;

@Controller
public class PurchaseController {

	private PurchaseRepository purchaseRepository;
	private ProductRepository productRepository;
	private SupplierRepository supplierRepository;
	
	public PurchaseController(
			PurchaseRepository purchaseRepository
			, ProductRepository productRepository
			, SupplierRepository supplierRepository
			) {
		this.purchaseRepository = purchaseRepository;
		this.productRepository = productRepository;
		this.supplierRepository = supplierRepository;
	}
	
	@GetMapping("/purchases")
	public String listPurchases(Model model) {
		model.addAttribute(purchaseRepository.findAll());
		return "purchases/purchaselist";
	}
	
	@GetMapping("/purchases/new")
	public String newPurchaseForm(Model model) {
		model.addAttribute(productRepository.findAll());
		model.addAttribute(supplierRepository.findAll());
		model.addAttribute(new Purchase());
		return "purchases/purchaseform";
	}
	
	@GetMapping("/purchases/{id}/edit")
	public String editPurchaseForm(Model model, @PathVariable long id) {
		model.addAttribute(productRepository.findAll());
		model.addAttribute(supplierRepository.findAll());
		model.addAttribute(purchaseRepository.findById(id).orElseGet(() -> new Purchase()));
		return "purchases/purchaseform";
	}
	
	@PostMapping({"/purchases"})
	public String savePurchase(
			@Valid Purchase purchase, Errors errors
			, final RedirectAttributes redirect
			, Model model
			) {
		if (errors.hasErrors()) {
			model.addAttribute(productRepository.findAll());
			model.addAttribute(supplierRepository.findAll());
			return "purchases/purchaseform";
		}
		purchaseRepository.save(purchase);
		redirect.addFlashAttribute("message", "New Purchase added successfully.");
		return "redirect:/purchases";
	}
}
