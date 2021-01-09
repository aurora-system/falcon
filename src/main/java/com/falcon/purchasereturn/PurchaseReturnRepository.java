package com.falcon.purchasereturn;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseReturnRepository extends CrudRepository<PurchaseReturn, Long>{

	List<PurchaseReturn> findAllByPurchaseNumber(String purchaseNumber);
}
