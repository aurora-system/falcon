package com.falcon.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.falcon.entity.Supplier;

public interface SupplierRepository extends PagingAndSortingRepository<Supplier, Long> {

	Optional<Supplier> findByName(String name);
}
