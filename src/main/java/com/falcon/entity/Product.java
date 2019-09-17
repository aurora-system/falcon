package com.falcon.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Product {

	@Id
	@GeneratedValue
	private long id;
	@NotNull
	private String name;
	@NotNull
	private String brand;
	@NotNull
	private String model;
	@NotNull
	private BigDecimal price;
	
	protected Product() {}
	
	public Product(@NotNull String name, @NotNull String brand, @NotNull String model,
			@NotNull BigDecimal price) {
		super();
		this.name = name;
		this.brand = brand;
		this.model = model;
		this.price = price;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}
