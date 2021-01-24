package com.falcon.purchasereturn;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseReturnRepository extends CrudRepository<PurchaseReturn, Long>{

    List<PurchaseReturn> findAllByPurchaseNumber(String purchaseNumber);

    List<PurchaseReturn> findAllByProductIdAndUnitCost(long productId, BigDecimal unitCost);
}
