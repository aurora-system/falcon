package com.falcon.activity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class UserActivity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Date date;
	private long userId;
	private String activity;
	private String subject;
}
