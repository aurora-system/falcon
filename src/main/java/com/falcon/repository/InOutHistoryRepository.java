package com.falcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.falcon.entity.InOutHistory;

public interface InOutHistoryRepository extends JpaRepository<InOutHistory, Long> {

	Page<InOutHistory> findAllByProductId(long productId, Pageable p);
}
