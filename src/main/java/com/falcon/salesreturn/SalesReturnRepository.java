package com.falcon.salesreturn;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.falcon.purchasereturn.PurchaseReturn;

public interface SalesReturnRepository extends CrudRepository<SalesReturn, Long>{

	List<SalesReturn> findAllByInvoiceNumber(String invoiceNumber);
	List<SalesReturn> findAllByProductIdAndSupplierIdAndUnitCost(long productId, long supplierId, BigDecimal unitCost);
}
