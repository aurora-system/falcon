package com.falcon.orders;

import java.util.ArrayList;
import java.util.List;

import com.falcon.product.Product;

import lombok.Data;

@Data
public class OrderForm {
	
	private Order order;
	
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	
}
