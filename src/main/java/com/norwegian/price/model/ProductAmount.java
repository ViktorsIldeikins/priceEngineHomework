package com.norwegian.price.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductAmount {

	private final int amountOfUnits;
	private final int amountOfCartons;

}
