package com.falcon.product;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_name_category", columnNames = "name")
})
@Data
public class ProductCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long categoryId;
	@NotBlank
	@Column(length = 32)
	private String name;
	
	@OneToMany(mappedBy = "productCategory", 
			fetch = FetchType.EAGER)
	List<Product> products;
}
