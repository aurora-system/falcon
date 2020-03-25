package com.falcon.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderId;
	private String type;
	private String customerName;
	private Date createdDate;
	private BigDecimal totalAmount;
	private String paymentType;
	private int monthlyDueDate;
	private String remarks;
	private String referenceNum;
	
	@OneToMany(mappedBy = "order", 
			fetch = FetchType.EAGER, 
			cascade = CascadeType.ALL)
	List<OrderItem> orderItems;
}
