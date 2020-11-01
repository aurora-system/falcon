package com.falcon.orders;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.falcon.product.Product;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id")
	@ToString.Exclude
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	@ToString.Exclude
	private Product product;
	
	private int quantity;
	private BigDecimal totalAmount;
}
