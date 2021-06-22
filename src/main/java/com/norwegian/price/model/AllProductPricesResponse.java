package com.norwegian.price.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AllProductPricesResponse {

	private String productName;
	private int amountOfUnitsInCarton;
	private List<PriceForAmountOfUnits> prices;

	@Getter
	@RequiredArgsConstructor
	public static class PriceForAmountOfUnits {
		private final BigDecimal price;
		private final int amountOfUnits;
		private final int amountOfCartons;
	}

	public static class AllProductPricesResponseBuilder {

		private List<PriceForAmountOfUnits> prices = new ArrayList<>();

		public AllProductPricesResponseBuilder withPrice(BigDecimal price, int amountOfUnits, int amountOfCartons) {
			prices.add(new PriceForAmountOfUnits(price, amountOfUnits, amountOfCartons));
			return this;
		}
	}
}
