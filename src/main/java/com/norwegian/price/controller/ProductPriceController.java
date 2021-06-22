package com.norwegian.price.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.norwegian.price.model.AllProductPricesResponse;
import com.norwegian.price.model.ProductPriceRequest;
import com.norwegian.price.model.ProductPriceResponse;
import com.norwegian.price.service.PriceCatalogService;
import com.norwegian.price.service.ProductPriceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductPriceController {

	private final PriceCatalogService priceCatalogService;
	private final ProductPriceService productPriceService;

	@GetMapping("price-catalog")
	public List<AllProductPricesResponse> getPriceCatalog() {
		return priceCatalogService.getAllProductPricesForDefinedAmounts();
	}

	@PostMapping("product-price")
	public List<ProductPriceResponse> getProductPrices(@RequestBody List<ProductPriceRequest> request) {
		return productPriceService.getProductPrice(request);
	}

}
