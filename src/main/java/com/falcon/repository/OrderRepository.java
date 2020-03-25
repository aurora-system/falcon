package com.falcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.falcon.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
