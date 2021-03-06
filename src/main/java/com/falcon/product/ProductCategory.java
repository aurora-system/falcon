package com.falcon.product;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_name_category", columnNames = "name")
})
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ProductCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NonNull
	@NotBlank
	@Column(length = 32)
	private String name;
	@JsonBackReference
	@OneToMany(mappedBy = "category")
	List<Product> products;
}
