package com.falcon.activity;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserActivityRepository extends PagingAndSortingRepository<UserActivity, Long> {

	List<UserActivity> findAllByDate(Date date);
	List<UserActivity> findAllByUserId(long userId);
	List<UserActivity> findAllByDateAndUserId(Date date, long userId);
}
