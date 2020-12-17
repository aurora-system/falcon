package com.falcon.expense;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.falcon.customer.Customer;

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
    
    @PostMapping("/expenses")
    @Transactional
    public String saveExpense(@Valid Expense expense, Model model, Errors errors, final RedirectAttributes redirect) {
	
	if (errors.hasErrors()) {
	    model.addAttribute("expenseForm", new Expense());
	    return "expense/expenseform";
	}
	expenseRepository.save(expense);
	return "redirect:/expenses";
    }
}
