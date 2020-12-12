package com.falcon.orders;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.falcon.customer.Customer;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name="ORDERS")
@Data
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String type; // service or sale
	private Date createdDate;
	@NotNull
	private BigDecimal totalAmount = BigDecimal.ZERO;
	private String paymentType;
	private int monthlyDueDate;
	private String remarks;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@OneToMany(mappedBy = "order", 
			fetch = FetchType.EAGER, 
			cascade = CascadeType.ALL)
	@ToString.Exclude
	List<OrderItem> orderItems;
}
