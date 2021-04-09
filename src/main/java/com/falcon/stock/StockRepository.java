package com.falcon.stock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, Long> {

    Optional<Stock> findBySkuAndUnitCost(String sku, BigDecimal unitCost);
    //Optional<Stock> findByProductIdAndUnitCost(long productId, BigDecimal unitCost);
    //Optional<Stock> findByProductIdAndSupplierIdAndUnitCost(long productId, long supplierId, BigDecimal unitCost);
    List<Stock> findAllBySupplierId(long supplierId);
    List<Stock> findAllByProductId(long productId);
}
