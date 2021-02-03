package com.falcon.salesreturn;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.falcon.salesorder.SalesOrder;

public interface SalesReturnRepository extends CrudRepository<SalesReturn, Long>{

	List<SalesReturn> findAllByInvoiceNumber(String invoiceNumber);
	List<SalesReturn> findAllByStockId(long stockId);
	List<SalesReturn> findByReturnDate(LocalDate returnDate);
}
