package com.falcon.product;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotBlank
	private String name;
	private String brand;
	private String otherDetails;
	private String forVehicle;
	private String color;
	@NotNull
	private BigDecimal aquiPrice = BigDecimal.ZERO;
	@NotNull
    private BigDecimal srp = BigDecimal.ZERO;
	private int stockLevel;
	private int threshold;
	private String supplierName;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	ProductCategory category;
	
}
