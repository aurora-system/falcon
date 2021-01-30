package com.falcon.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import com.falcon.config.data.Auditable;
import com.falcon.product.Product;
import com.falcon.supplier.Supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data public class Purchase extends Auditable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate transDate = LocalDate.now();
    @NotBlank
    private String purchaseNumber;
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private long quantity;
    private BigDecimal unitCost = BigDecimal.ZERO;
    private boolean isDeleted = false;

    public BigDecimal getTotalAmount() {
        return this.unitCost.multiply(new BigDecimal(this.quantity));
    }
}
