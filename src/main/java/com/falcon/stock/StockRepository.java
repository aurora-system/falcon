package com.falcon.stock;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, Long> {

	Optional<Stock> findByProductIdAndUnitCost(long productId, BigDecimal unitCost);
}
