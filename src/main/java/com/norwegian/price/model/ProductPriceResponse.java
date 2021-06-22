package com.norwegian.price.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductPriceResponse {

	private Long id;
	private String name;
	private BigDecimal price;
	private int amountOfUnits;
	private int amountOfCartons;

}
