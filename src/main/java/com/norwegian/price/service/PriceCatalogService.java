package com.norwegian.price.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.norwegian.price.model.AllProductPricesResponse;
import com.norwegian.price.model.CalculatedProduct;
import com.norwegian.price.model.Product;
import com.norwegian.price.model.ProductAmount;
import com.norwegian.price.persistance.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PriceCatalogService {

	private final ProductRepository productRepository;
	private final ProductCalculator productCalculator;


	public List<AllProductPricesResponse> getAllProductPricesForDefinedAmounts() {
		return productRepository.findAll().stream()
				.map(this::calculateAllProductPrices)
				.collect(Collectors.toList());
	}

	private AllProductPricesResponse calculateAllProductPrices(Product product) {
		AllProductPricesResponse.AllProductPricesResponseBuilder productPricesBuilder = AllProductPricesResponse
				.builder()
				.productName(product.getName())
				.amountOfUnitsInCarton(product.getUnitsInCarton());
		for(int i = 1; i <= 50; i++) {
			ProductAmount productAmount = new ProductAmount(i, 0);
			CalculatedProduct calculatedProduct = productCalculator.calculateProduct(product, productAmount);
			ProductAmount calculatedAmount = calculatedProduct.getProductAmount();
			productPricesBuilder.withPrice(calculatedProduct.getPrice(),
					calculatedAmount.getAmountOfUnits(), calculatedAmount.getAmountOfCartons());
		}
		return productPricesBuilder.build();
	}

}
