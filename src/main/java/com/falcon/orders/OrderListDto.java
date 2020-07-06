package com.falcon.orders;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderListDto {
	
	List<OrderDto> orderList;
}
