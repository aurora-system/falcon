package com.falcon.expense;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ExpenseDto {

	private long expenseId;
	private String type;
	private BigDecimal amount;
	private String remarks;
	private Date expenseDate;
}
