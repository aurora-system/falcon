package com.falcon.stock;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.falcon.product.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data
public class StockHistory {

    private LocalDate transDate;
    private Product product;
    private BigDecimal unitCost = BigDecimal.ZERO;
    private long quantity;
    private String type;
    private long updatedCount;
}
