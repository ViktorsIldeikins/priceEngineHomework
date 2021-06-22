package com.norwegian.price.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.norwegian.price.model.ProductAmount;

class ProductAmountOptimizerTest {

	private final ProductAmountOptimizer amountOptimizer = new ProductAmountOptimizer();

	@Test
	void amountOfUnitsLessThanUnitsInCarton_nothingToOptimize() {
		ProductAmount result = amountOptimizer.optimizeProductAmount(new ProductAmount(10, 3), 30);
		assertThat(result.getAmountOfUnits()).isEqualTo(10);
		assertThat(result.getAmountOfCartons()).isEqualTo(3);

	}

	@Test
	void amountOfUnitsIsHigherThanUnitsInCarton_unitsArePackagedInCarton() {
		ProductAmount result = amountOptimizer.optimizeProductAmount(new ProductAmount(13, 0), 10);
		assertThat(result.getAmountOfUnits()).isEqualTo(3);
		assertThat(result.getAmountOfCartons()).isEqualTo(1);
	}

	@Test
	void amountOfUnitsIsHigherThanUnitsInCarton_someCartonsAreRequested_unitsArePackagedInCarton() {
		ProductAmount result = amountOptimizer.optimizeProductAmount(new ProductAmount(13, 3), 10);
		assertThat(result.getAmountOfUnits()).isEqualTo(3);
		assertThat(result.getAmountOfCartons()).isEqualTo(4);
	}
}