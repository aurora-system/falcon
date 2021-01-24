package com.falcon.stock;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface SalesOrderProjection {

    LocalDate getTransDate();
    BigDecimal getUnitCost();
    long getQuantity();
}
