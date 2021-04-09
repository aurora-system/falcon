package com.falcon.salesorder;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data public class Customer {

	private String name;
	private String address;
	private String tin;
	private String contactNumber;
	private String email;
}
