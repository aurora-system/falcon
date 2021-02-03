package com.falcon.report;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.falcon.stock.Stock;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyDto {
    
    private String number;
    private LocalDate transDate;
    private Stock stock;
    private String type;
    private long quantity;
    private BigDecimal totalAmount;
    private BigDecimal profit;
}
