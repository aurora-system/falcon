package com.falcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.falcon.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
