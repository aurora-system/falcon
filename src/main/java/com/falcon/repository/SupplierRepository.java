package com.falcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.falcon.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

	Supplier findByName(String name);
}
