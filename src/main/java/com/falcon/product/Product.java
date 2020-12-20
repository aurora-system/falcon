package com.falcon.product;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.falcon.orders.OrderItem;

import lombok.Data;
import lombok.ToString;

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
	@NotBlank
	private String brand;
	private String otherDetails;
	private String forVehicle;
	private String color;
	@NotNull
	@PositiveOrZero
	private BigDecimal aquiPrice = BigDecimal.ZERO;
	@NotNull
	@PositiveOrZero
	private BigDecimal srp = BigDecimal.ZERO;
	@PositiveOrZero
	private int stockLevel;
	@PositiveOrZero
	private int threshold;
	private String supplierName;
	
	@OneToMany(mappedBy = "product")
	@ToString.Exclude
	List<OrderItem> orderItemList;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	@ToString.Exclude
	ProductCategory category;
	
}
