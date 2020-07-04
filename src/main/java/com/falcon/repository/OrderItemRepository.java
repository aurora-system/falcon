package com.falcon.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.falcon.entity.OrderItem;

public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, Long> {

}
