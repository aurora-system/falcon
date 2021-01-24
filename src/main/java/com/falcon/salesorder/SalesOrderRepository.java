package com.falcon.salesorder;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.falcon.stock.SalesOrderProjection;

public interface SalesOrderRepository extends CrudRepository<SalesOrder, Long>{

    @Query("select o from SalesOrder o where o.invoiceNumber like %:keyword% OR o.remarks like %:keyword%")
    List<SalesOrder> findAllByInvoiceNumberOrRemarks(String keyword);

    List<SalesOrder> findByTransDate(LocalDate transDate);

    List<SalesOrder> findByTransDateAndStatus(LocalDate transDate, String status);

    List<SalesOrder> findByTransDateGreaterThanAndStatus(LocalDate transDate, String status);

    @Query(value = "SELECT * FROM sales_order_items WHERE stock_id = :stockId", nativeQuery = true)
    List<SalesOrderItem> findAllByStockId(long stockId);

    @Query(nativeQuery = true,
            value = "SELECT so.trans_date as transDate, (soi.selling_price - soi.discount_price) as unitCost, soi.quantity as quantity FROM sales_order so INNER JOIN sales_order_items soi ON so.id = soi.sales_order_id WHERE soi.stock_id = :stockId")
    List<SalesOrderProjection> findHistoryByStockId(long stockId);

    //@Query(value = "SELECT so.trans_date,soi.* FROM sales_order so INNER JOIN sales_order_items soi ON so.id = soi.sales_order_id WHERE soi.stock_id = :stockId"
    //        , nativeQuery = true)
    //List<Object[]> findAllByStockId(long stockId);
}
