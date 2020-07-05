package com.falcon.customer;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

	Optional<Customer> findByName(String name);
}
