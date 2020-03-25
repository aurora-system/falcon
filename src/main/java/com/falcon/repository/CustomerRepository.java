package com.falcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.falcon.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByName(String name);
}
