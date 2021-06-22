package com.norwegian.price.service;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import com.norwegian.price.model.AllProductPricesResponse;
import com.norwegian.price.model.CalculatedProduct;
import com.norwegian.price.model.Product;
import com.norwegian.price.model.ProductAmount;
import com.norwegian.price.persistance.ProductRepository;

@ExtendWith(MockitoExtension.class)
class PriceCatalogServiceTest {

	private static final String PRODUCT_NAME = "productName";
	private static final int UNITS_IN_CARTON = 10;

	@Mock
	private ProductRepository productRepository;
	@Mock
	private ProductCalculator productCalculator;
	@InjectMocks
	private PriceCatalogService priceCatalogService;

	@Test
	void getAllProductPricesForDefinedAmounts() {
		Product product = productFrom(PRODUCT_NAME, UNITS_IN_CARTON);
		given(productRepository.findAll()).willReturn(asList(product));
		CalculatedProduct calculatedProduct = new CalculatedProduct(
				product, new ProductAmount(10, 0), BigDecimal.valueOf(10));
		given(productCalculator.calculateProduct(eq(product), any())).willReturn(calculatedProduct);

		List<AllProductPricesResponse> result = priceCatalogService.getAllProductPricesForDefinedAmounts();

		assertThat(result.size()).isEqualTo(1);
		AllProductPricesResponse productPricesResponse = result.get(0);
		assertThat(productPricesResponse.getProductName()).isEqualTo(PRODUCT_NAME);
		assertThat(productPricesResponse.getAmountOfUnitsInCarton()).isEqualTo(UNITS_IN_CARTON);
		assertThat(productPricesResponse.getPrices().size()).isEqualTo(50);
		assertThat(productPricesResponse.getPrices()).allMatch(resultPrice ->
				resultPrice.getPrice().equals(BigDecimal.valueOf(10)) &&
				resultPrice.getAmountOfUnits() == 10 &&
				resultPrice.getAmountOfCartons() == 0);
	}

	private Product productFrom(String name, int unitsInCarton) {
		Product product = new Product();
		product.setName(name);
		product.setUnitsInCarton(unitsInCarton);
		return product;
	}
}