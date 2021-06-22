package com.norwegian.price.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CalculatedProduct {

	private Product product;
	private ProductAmount productAmount;
	private BigDecimal price;

}
