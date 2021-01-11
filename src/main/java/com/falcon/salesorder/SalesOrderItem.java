package com.falcon.salesorder;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.falcon.stock.Stock;

import lombok.Data;
import lombok.ToString;

@Embeddable
@Data public class SalesOrderItem {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stock_id", nullable = false)
	@ToString.Exclude
	private Stock stock;
	@Column(nullable = false)
	private long quantity;
	@Column(nullable = false)
	private BigDecimal sellingPrice = BigDecimal.ZERO;
	@Column(nullable = false)
	private BigDecimal discountPrice = BigDecimal.ZERO;
	
	public BigDecimal getNetSellingPrice() {
		return sellingPrice
				.subtract(discountPrice)
				.multiply(new BigDecimal(quantity));
	}
}
