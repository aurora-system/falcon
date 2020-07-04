package com.falcon.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.falcon.entity.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

}
