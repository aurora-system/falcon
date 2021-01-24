package com.falcon.purchase;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase, Long>{

    List<Purchase> findAllByProductIdAndUnitCost(long productId, BigDecimal unitCost);
}
