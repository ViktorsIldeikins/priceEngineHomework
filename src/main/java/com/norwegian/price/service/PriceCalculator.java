package com.norwegian.price.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

import com.norwegian.price.model.Product;
import com.norwegian.price.model.ProductAmount;

@Component
public class PriceCalculator {

	private static final double MANY_CARTONS_DISCOUNT = 0.1;
	private static final double BUY_BY_UNITS_MARK_UP = 0.3;

	public BigDecimal calculatePrice(Product product, ProductAmount productAmount) {
		BigDecimal priceOfCartons = getPriceOfCartons(product.getCostOfCarton(), productAmount.getAmountOfCartons());
		BigDecimal priceOfUnits = getPriceOfUnits(product, productAmount.getAmountOfUnits());
		return priceOfCartons.add(priceOfUnits).setScale(2, RoundingMode.HALF_UP);
	}

	private BigDecimal getPriceOfCartons(BigDecimal costOfCarton, int amountOfCartons) {
		BigDecimal priceOfCartons = costOfCarton.multiply(BigDecimal.valueOf(amountOfCartons));
		if (amountOfCartons >= 3) {
			priceOfCartons = priceOfCartons.multiply(BigDecimal.valueOf(1 - MANY_CARTONS_DISCOUNT));
		}
		return priceOfCartons;
	}

	private BigDecimal getPriceOfUnits(Product product, int amountOfUnits) {
		BigDecimal costOfUnit = product.getCostOfCarton()
				.divide(BigDecimal.valueOf(product.getUnitsInCarton()), 4, RoundingMode.HALF_UP)
				.multiply(BigDecimal.valueOf(1 + BUY_BY_UNITS_MARK_UP));
		return costOfUnit.multiply(BigDecimal.valueOf(amountOfUnits));
	}

}
