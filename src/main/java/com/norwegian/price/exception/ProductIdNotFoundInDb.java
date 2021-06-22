package com.norwegian.price.exception;

public class ProductIdNotFoundInDb extends RuntimeException {

	public ProductIdNotFoundInDb(Long productId) {
		super("Product id " + productId + " not found in database");
	}
}
