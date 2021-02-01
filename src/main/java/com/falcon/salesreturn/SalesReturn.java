package com.falcon.salesreturn;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

import com.falcon.config.data.Auditable;
import com.falcon.product.Product;
import com.falcon.stock.Stock;
import com.falcon.supplier.Supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data public class SalesReturn extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotBlank
    private String invoiceNumber;
    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate = LocalDate.now();
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
    private long quantity;
    
    public BigDecimal getTotalAmount() {
        return new BigDecimal(quantity).multiply(stock.getUnitCost());
    }
}
