package com.falcon.salesorder;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SalesOrderRepository extends CrudRepository<SalesOrder, Long>{

    @Query("select o from SalesOrder o where o.invoiceNumber like %:keyword% OR o.remarks like %:keyword%")
    List<SalesOrder> findAllByInvoiceNumberOrRemarks(String keyword);

    List<SalesOrder> findByTransDate(LocalDate transDate);
    
    List<SalesOrder> findByTransDateGreaterThan(LocalDate transDate);
}
