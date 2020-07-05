package com.falcon.inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InOutHistoryRepository extends PagingAndSortingRepository<InOutHistory, Long> {

	Page<InOutHistory> findAllByProductId(long productId, Pageable p);
}
