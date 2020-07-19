package com.falcon.customer;

import lombok.Data;

@Data
public class CustomerDto {

	private long customerId;
	private String name;
	private String address;
	private String contactNumber;
}
