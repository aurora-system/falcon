package com.falcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.falcon.entity.InOutHistory;

public interface InOutHistoryRepository extends PagingAndSortingRepository<InOutHistory, Long> {

	Page<InOutHistory> findAllByProductId(long productId, Pageable p);
}
