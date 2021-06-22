package com.norwegian.price.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPriceRequest {

	private Long id;
	private int amountOfUnits;
	private int amountOfCartons;

}
