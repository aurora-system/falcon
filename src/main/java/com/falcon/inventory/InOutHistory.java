package com.falcon.inventory;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import com.falcon.product.Product;

import lombok.Data;

@Entity
@Data
public class InOutHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long historyId;
	private Date transDate;
	@Size(min = 2, max = 3, message = "Transaction type is either 'IN' or 'OUT'")
	private String transType;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;
	private int productCount;
	private int updatedCount;
}