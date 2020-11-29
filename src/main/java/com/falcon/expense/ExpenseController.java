package com.falcon.expense;

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
	return "expense/expenselist";
    }
    
    @GetMapping({"/expenses/new"})
    public String newExpenseForm(Model model) {
	model.addAttribute("expenseForm", new Expense());
	return "expense/expenseform";
    }
}
