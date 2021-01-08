package com.falcon.stock;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, Long> {

	List<Stock> findAllByProductIdAndUnitCost(long productId, BigDecimal unitCost);
}
