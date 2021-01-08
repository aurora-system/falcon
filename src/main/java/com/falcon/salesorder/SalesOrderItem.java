package com.falcon.salesorder;

import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.falcon.stock.Stock;

import lombok.Data;
import lombok.ToString;

@Embeddable
@Data public class SalesOrderItem {

	@ManyToOne
	@JoinColumn(name = "stock_id")
	@ToString.Exclude
	private Stock stock;
	private long quantity;
	private BigDecimal sellingPrice = BigDecimal.ZERO;
	private BigDecimal discountPrice = BigDecimal.ZERO;
	
	public BigDecimal getNetSellingPrice() {
		return sellingPrice
				.subtract(discountPrice)
				.multiply(new BigDecimal(quantity));
	}
}
