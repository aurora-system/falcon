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
	
	@GetMapping("/expenses")
    public String listExpenses(Model model) {
        
		List<ExpenseDto> expenseList = new ArrayList<ExpenseDto>();
		
		ExpenseDto dto1 = new ExpenseDto();
		dto1.setAmount(new BigDecimal(100.00));
		dto1.setType("service");
		dto1.setRemarks("This is a remark");
		dto1.setExpenseDate(new Date());
		expenseList.add(dto1);
		
		model.addAttribute("expenseList", expenseList);
		model.addAttribute("message", "Hello from @GetMapping.");
		return "expense/expenseList";
    }
}
