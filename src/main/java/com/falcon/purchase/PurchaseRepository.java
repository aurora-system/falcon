package com.falcon.purchase;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase, Long>{

    List<Purchase> findAllByIsDeleted(boolean isDeleted);
    List<Purchase> findAllByProductIdAndUnitCost(long productId, BigDecimal unitCost);
    List<Purchase> findAllByProductIdAndSupplierIdAndUnitCost(long productId, long supplierId, BigDecimal unitCost);
}
