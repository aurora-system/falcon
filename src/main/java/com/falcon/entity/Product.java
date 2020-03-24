package com.falcon.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Product {

	@Id
	@GeneratedValue
	private long id;
	@NotNull
	private String name;
	private String brand;
	private String otherDetails;
	private String forVehicle;
	private String color;
	private BigDecimal aquiPrice;
	private BigDecimal srp;
	private int stockLevel;
	private int threshold;
	private String supplierName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	ProductCategory productCategory;
	
}
