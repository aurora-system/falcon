package com.falcon.orders;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
	
    //Page<Order> findAllById(long orderId, Pageable page);
    
    @Query("select o from Order o where o.remarks like %:key%")
    List<Order> findByRemarks(String key);
    
    List<Order> findByCreatedDate(LocalDate date);
}
