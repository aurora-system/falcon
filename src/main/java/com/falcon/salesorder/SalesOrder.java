package com.falcon.salesorder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import com.falcon.config.data.Auditable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data public class SalesOrder extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String type; // service or sale
	@PastOrPresent
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate transDate = LocalDate.now();
	@NotBlank
	private String invoiceNumber;
	@NotNull
	private BigDecimal totalAmount = BigDecimal.ZERO;
	private String paymentType;
	private int monthlyDueDate;
	private String remarks;
	private String status = "PROCESSED"; // { PROCESSED, CANCELLED }
	@Embedded
	@AttributeOverrides(value = {
		@AttributeOverride(name = "name", column = @Column(name = "customer_name")),
		@AttributeOverride(name = "address", column = @Column(name = "customer_address")),
		@AttributeOverride(name = "tin", column = @Column(name = "customer_tin")),
		@AttributeOverride(name = "contactNumber", column = @Column(name = "customer_contact_number"))
	})
	private Customer customer;
	@ElementCollection
	@ToString.Exclude
	List<SalesOrderItem> salesOrderItems = new ArrayList<>();
	
}
