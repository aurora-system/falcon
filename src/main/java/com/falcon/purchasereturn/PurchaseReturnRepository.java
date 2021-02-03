package com.falcon.purchasereturn;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseReturnRepository extends CrudRepository<PurchaseReturn, Long>{

    List<PurchaseReturn> findAllByPurchaseNumber(String purchaseNumber);
    
    List<PurchaseReturn> findAllByProductIdAndUnitCost(long productId, BigDecimal unitCost);
    
    List<PurchaseReturn> findAllByProductIdAndSupplierIdAndUnitCost(long productId, long supplierId, BigDecimal unitCost);
    
    List<PurchaseReturn> findByReturnDate(LocalDate returnDate);
}
