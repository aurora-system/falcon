package com.falcon.product;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_name_product", columnNames = "name")
})
@Data
public class Product {

	@Id
	@GeneratedValue
	private long id;
	@NotBlank
	private String name;
	private String brand;
	private String otherDetails;
	private String forVehicle;
	private String color;
	@NotNull
	private BigDecimal aquiPrice;
	@NotNull
    private BigDecimal srp;
	private int stockLevel;
	private int threshold;
	private String supplierName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	ProductCategory productCategory;
	
}
