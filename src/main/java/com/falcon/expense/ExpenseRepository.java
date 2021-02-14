package com.falcon.expense;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Long> {

    List<Expense> findByExpenseDate(LocalDate date);
    
    @Query("SELECT DISTINCT(type) FROM Expense")
    List<String> findAllTypes();
}
