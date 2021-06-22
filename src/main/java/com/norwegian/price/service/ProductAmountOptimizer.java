package com.norwegian.price.service;

import org.springframework.stereotype.Component;

import com.norwegian.price.model.ProductAmount;

@Component
public class ProductAmountOptimizer {

	public ProductAmount optimizeProductAmount(ProductAmount productAmount, int unitsInCarton) {
		return new ProductAmount(
				calculateUnitAmount(unitsInCarton, productAmount),
				calculateCartonAmount(unitsInCarton, productAmount));
	}

	private int calculateUnitAmount(int unitsInCarton, ProductAmount productAmount) {
		return productAmount.getAmountOfUnits() % unitsInCarton;
	}

	private int calculateCartonAmount(int unitsInCarton, ProductAmount productAmount) {
		return productAmount.getAmountOfUnits() / unitsInCarton + productAmount.getAmountOfCartons();
	}

}
