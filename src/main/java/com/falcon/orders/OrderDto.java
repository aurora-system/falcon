package com.falcon.orders;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class OrderDto {
	
	private long orderId;
	private String type;
	private String customerName;
	private Date createdDate;
	private BigDecimal totalAmount;
	private String paymentType;
	private int monthlyDueDate;
	private String remarks;
	private String referenceNum;
}
