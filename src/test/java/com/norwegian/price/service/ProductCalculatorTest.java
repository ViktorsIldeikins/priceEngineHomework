package com.norwegian.price.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.norwegian.price.model.CalculatedProduct;
import com.norwegian.price.model.Product;
import com.norwegian.price.model.ProductAmount;

@ExtendWith(MockitoExtension.class)
class ProductCalculatorTest {

	private static final int UNITS_IN_CARTON = 15;
	private static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(100);
	@Mock
	private PriceCalculator priceCalculator;
	@Mock
	private ProductAmountOptimizer amountOptimizer;
	@InjectMocks
	private ProductCalculator productCalculator;

	@Mock
	private Product product;
	@Mock
	private ProductAmount productAmount;
	@Mock
	private ProductAmount optimizedAmount;

	@Test
	void calculateProduct() {
		given(product.getUnitsInCarton()).willReturn(UNITS_IN_CARTON);
		given(amountOptimizer.optimizeProductAmount(eq(productAmount), eq(UNITS_IN_CARTON))).willReturn(optimizedAmount);
		given(priceCalculator.calculatePrice(eq(product), eq(optimizedAmount))).willReturn(PRODUCT_PRICE);

		CalculatedProduct result = productCalculator.calculateProduct(product, productAmount);

		assertThat(result.getPrice()).isEqualTo(PRODUCT_PRICE);
		assertThat(result.getProduct()).isEqualTo(product);
		assertThat(result.getProductAmount()).isEqualTo(optimizedAmount);

	}

}