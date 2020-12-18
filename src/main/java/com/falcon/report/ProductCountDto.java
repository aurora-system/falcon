package com.falcon.report;

import com.falcon.product.Product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductCountDto {
    private Product product;
    private int count = 0;
}
