package com.falcon.customer;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface SupplierRepository extends PagingAndSortingRepository<Supplier, Long> {

	Optional<Supplier> findByName(String name);
}
