package com.falcon.supplier;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SupplierController {

private SupplierRepository supplierRepository;
	
	public SupplierController(
			SupplierRepository supplierRepository
			) {
		this.supplierRepository = supplierRepository;
	}
	
	@GetMapping("/suppliers")
	public String listSuppliers(Model model) {
		model.addAttribute(supplierRepository.findAll());
		return "supplier/supplierlist";
	}
	
	@GetMapping("/suppliers/new")
	public String newSupplierForm(Model model) {
		model.addAttribute(new Supplier());
		return "supplier/supplierform";
	}
	
	@GetMapping("/suppliers/{id}/edit")
	public String editSupplierForm(Model model, @PathVariable long id) {
		model.addAttribute(supplierRepository.findById(id).orElseGet(() -> new Supplier()));
		return "supplier/supplierform";
	}
	
	@PostMapping({"/suppliers"})
	public String saveSupplier(
			@Valid Supplier supplier, Errors errors
			, final RedirectAttributes redirect
			, Model model
			) {
		if (errors.hasErrors()) {
			model.addAttribute(supplierRepository.findAll());
			return "supplier/supplierform";
		}
		supplierRepository.save(supplier);
		redirect.addFlashAttribute("message", "New Supplier added successfully.");
		return "redirect:/suppliers";
	}
}
