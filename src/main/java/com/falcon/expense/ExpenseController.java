package com.falcon.expense;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExpenseController {
	
	private ExpenseRepository expenseRepository;
	
	public ExpenseController(ExpenseRepository expenseRepository) {
		this.expenseRepository = expenseRepository;
	}
	
	@GetMapping("/expenses")
	public String listExpenses(Model model) {
		Iterable<Expense> expenseList = expenseRepository.findAll();
		model.addAttribute("expenses", expenseList);
		model.addAttribute("message", "Hello from @GetMapping.");
		return "expense/expenseList";
    }
}
