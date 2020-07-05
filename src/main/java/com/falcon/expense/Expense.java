package com.falcon.expense;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long expenseId;
	@Column(length = 50)
	private String type;
	@Column(precision = 10, scale = 2)
	private BigDecimal amount;
	private String remarks;
	private Date expenseDate;
}
