package com.norwegian.price.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.norwegian.price.model.CalculatedProduct;
import com.norwegian.price.model.Product;
import com.norwegian.price.model.ProductAmount;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductCalculator {

	private final PriceCalculator priceCalculator;
	private final ProductAmountOptimizer amountOptimizer;

	public CalculatedProduct calculateProduct(Product product, ProductAmount productAmount) {
		ProductAmount optimizedAmount = amountOptimizer.optimizeProductAmount(productAmount, product.getUnitsInCarton());
		BigDecimal price = priceCalculator.calculatePrice(product, optimizedAmount);
		return new CalculatedProduct(product, optimizedAmount, price);
	}
}
