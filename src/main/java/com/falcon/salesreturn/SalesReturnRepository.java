package com.falcon.salesreturn;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface SalesReturnRepository extends CrudRepository<SalesReturn, Long>{

	List<SalesReturn> findAllByInvoiceNumber(String invoiceNumber);
	List<SalesReturn> findAllByStockId(long stockId);
}
