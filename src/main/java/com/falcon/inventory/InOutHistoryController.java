package com.falcon.inventory;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.falcon.product.ProductRepository;

@Controller
public class InOutHistoryController {
	
	private InOutHistoryRepository inOutHistoryRepository;
	private ProductRepository productRepository;
	
	public InOutHistoryController(
			InOutHistoryRepository inOutHistoryRepository
			,ProductRepository productRepository
			) {
		this.inOutHistoryRepository = inOutHistoryRepository;
		this.productRepository = productRepository;
	}

	@GetMapping({"/inouthistory"})
	public String listAllInOutHistory(Model model) {
		model.addAttribute("inouthistories", inOutHistoryRepository.findAll());
		return "inventory/inouthistory";
	}
	
	@GetMapping({"/inouthistory/new"})
	public String newInOutHistoryForm(Model model) {
		model.addAttribute("inOutHistory", new InOutHistory());
		model.addAttribute("products", productRepository.findAll());
		return "inventory/inouthistoryform";
	}
	
	@PostMapping({"/inouthistory"})
	public String saveInOutHistory(
			@Valid InOutHistory inOutHistory, Errors errors
			, final RedirectAttributes redirect
			, Model model
			) {
		if (errors.hasErrors()) {
			model.addAttribute("products", productRepository.findAll());
			return "inventory/inouthistoryform";
		}
		inOutHistoryRepository.save(inOutHistory);
		redirect.addFlashAttribute("message", "In/Out transaction saved successfully.");
		return "redirect:/inouthistory";
	}
}
