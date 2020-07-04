package com.falcon.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.falcon.entity.Expense;

public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Long> {

}
