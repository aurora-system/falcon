package com.falcon.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.falcon.entity.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

	Optional<Customer> findByName(String name);
}
