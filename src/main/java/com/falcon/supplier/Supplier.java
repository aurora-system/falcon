package com.falcon.supplier;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.falcon.config.data.Auditable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data public class Supplier extends Auditable {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotBlank
	private String name;
	private String address;
	private String contactNumber;
	
}
