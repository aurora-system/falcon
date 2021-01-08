package com.falcon.stock;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.falcon.config.data.Auditable;
import com.falcon.product.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data public class Stock extends Auditable {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	@PositiveOrZero
	private long quantity;
	@NotNull @PositiveOrZero
	private BigDecimal unitCost = BigDecimal.ZERO;
	@NotNull @PositiveOrZero
	private BigDecimal markup = BigDecimal.ZERO;
	@PositiveOrZero
	private long lowCountIndicator;
}
