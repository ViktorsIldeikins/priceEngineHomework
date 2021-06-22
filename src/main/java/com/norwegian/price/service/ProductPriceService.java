package com.norwegian.price.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.norwegian.price.exception.ProductIdNotFoundInDb;
import com.norwegian.price.model.CalculatedProduct;
import com.norwegian.price.model.Product;
import com.norwegian.price.model.ProductAmount;
import com.norwegian.price.model.ProductPriceRequest;
import com.norwegian.price.model.ProductPriceResponse;
import com.norwegian.price.persistance.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductPriceService {

	private final ProductRepository productRepository;
	private final ProductCalculator productCalculator;

	public List<ProductPriceResponse> getProductPrice(List<ProductPriceRequest> request) {
		return request.stream()
				.map(this::getProductPriceResponse)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	private ProductPriceResponse getProductPriceResponse(ProductPriceRequest productPriceRequest) {
		return productRepository.findById(productPriceRequest.getId())
				.map(product -> getCalculatedProduct(productPriceRequest, product))
				.map(this::toResponse)
				.orElseThrow(() -> new ProductIdNotFoundInDb(productPriceRequest.getId()));
	}

	private CalculatedProduct getCalculatedProduct(ProductPriceRequest productPriceRequest, Product product) {
		ProductAmount productAmount = new ProductAmount(
				productPriceRequest.getAmountOfUnits(),
				productPriceRequest.getAmountOfCartons());
		return productCalculator.calculateProduct(product, productAmount);
	}

	private ProductPriceResponse toResponse(CalculatedProduct calculatedProduct) {
		return new ProductPriceResponse(
				calculatedProduct.getProduct().getId(),
				calculatedProduct.getProduct().getName(),
				calculatedProduct.getPrice(),
				calculatedProduct.getProductAmount().getAmountOfUnits(),
				calculatedProduct.getProductAmount().getAmountOfCartons()
		);
	}
}
