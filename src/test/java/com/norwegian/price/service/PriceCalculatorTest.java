package com.norwegian.price.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.norwegian.price.model.Product;
import com.norwegian.price.model.ProductAmount;

@ExtendWith(MockitoExtension.class)
class PriceCalculatorTest {

	private static final BigDecimal COST_OF_CARTON = BigDecimal.valueOf(70).setScale(2, RoundingMode.HALF_UP);
	private static final int UNITS_IN_CARTON = 25;
	private PriceCalculator priceCalculator = new PriceCalculator();

	@Mock
	private Product product;

	@BeforeEach
	public void setup() {
		given(product.getCostOfCarton()).willReturn(COST_OF_CARTON);
		given(product.getUnitsInCarton()).willReturn(UNITS_IN_CARTON);
	}

	@Test
	void onlyCartonsAreRequested() {
		BigDecimal result = priceCalculator.calculatePrice(product, new ProductAmount(0, 2));

		assertThat(result).isEqualTo(COST_OF_CARTON.multiply(BigDecimal.valueOf(2)));
	}

	@Test
	void onlyCartonsAreRequested_moreThan3Cartons_DiscountApplied() {
		BigDecimal result = priceCalculator.calculatePrice(product, new ProductAmount(0, 4));

		BigDecimal expectedResult = COST_OF_CARTON
				.multiply(BigDecimal.valueOf(4))
				.multiply(BigDecimal.valueOf(0.9))
				.setScale(2, RoundingMode.HALF_UP);
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	void onlyUnitsAreRequested() {
		BigDecimal result = priceCalculator.calculatePrice(product, new ProductAmount(10, 0));

		BigDecimal expectedResult = COST_OF_CARTON
				.divide(BigDecimal.valueOf(UNITS_IN_CARTON), 2, RoundingMode.HALF_UP)
				.multiply(BigDecimal.valueOf(1.3))
				.multiply(BigDecimal.valueOf(10))
				.setScale(2, RoundingMode.HALF_UP);
		assertThat(result).isEqualTo(expectedResult);
	}

	@Test
	void unitsAndCartonsAreRequested_priceIsSummedForBothQuantities() {
		BigDecimal result = priceCalculator.calculatePrice(product, new ProductAmount(10, 2));

		BigDecimal priceOfUnits = COST_OF_CARTON
				.divide(BigDecimal.valueOf(UNITS_IN_CARTON), 2, RoundingMode.HALF_UP)
				.multiply(BigDecimal.valueOf(1.3))
				.multiply(BigDecimal.valueOf(10));
		BigDecimal priceOfCartons = COST_OF_CARTON.multiply(BigDecimal.valueOf(2));
		assertThat(result).isEqualTo(priceOfUnits.add(priceOfCartons).setScale(2, RoundingMode.HALF_UP));
	}



}