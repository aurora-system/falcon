package com.falcon.expense;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExpenseController {

    private ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/expenses")
    public String listExpenses(Model model) {
        Iterable<Expense> expenseList = this.expenseRepository.findAll();
        List<Expense> expenses = new ArrayList<>();
        expenseList.forEach(expenses::add);
        Collections.sort(expenses, (a,b)
                -> a.getExpenseDate().isBefore(b.getExpenseDate()) ? -1 :
                    a.getExpenseDate().isAfter(b.getExpenseDate()) ? 1 : 0);
        model.addAttribute("expenses", expenseList);
        return "expense/expenselist";
    }

    @GetMapping({"/expenses/new"})
    public String newExpenseForm(Model model) {
        model.addAttribute("expenseForm", new Expense());
        return "expense/expenseform";
    }

    @GetMapping("/expenses/{id}/edit")
    public String editExpenseForm(Model model
            , @PathVariable long id) {
        model.addAttribute("expenseForm", this.expenseRepository.findById(id).orElseGet(Expense::new));
        return "expense/expenseform";
    }

    @PostMapping("/expenses")
    @Transactional
    public String saveExpense(@Valid Expense expense
            , Model model
            , Errors errors
            , final RedirectAttributes redirect) {

        String successMsg = "";
        if (expense.getId() != 0) {
            successMsg = "Expense edited successfully.";
        } else {
            successMsg = "New expense added successfully.";
        }

        if (errors.hasErrors()) {
            model.addAttribute("expenseForm", new Expense());
            return "expense/expenseform";
        }
        this.expenseRepository.save(expense);
        redirect.addFlashAttribute("message", successMsg);
        return "redirect:/expenses";
    }
}
