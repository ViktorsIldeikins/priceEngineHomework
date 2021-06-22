package com.norwegian.price.service;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.norwegian.price.exception.ProductIdNotFoundInDb;
import com.norwegian.price.model.CalculatedProduct;
import com.norwegian.price.model.Product;
import com.norwegian.price.model.ProductAmount;
import com.norwegian.price.model.ProductPriceRequest;
import com.norwegian.price.model.ProductPriceResponse;
import com.norwegian.price.persistance.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductPriceServiceTest {

	private static final long PRODUCT_ID = 3L;
	private static final String PRODUCT_NAME = "product name";
	private static final int CALCULATED_AMOUNT_OF_UNITS = 12;
	private static final int CALCULATED_AMOUNT_OD_CARTONS = 4;
	private static final BigDecimal PRICE = BigDecimal.valueOf(150);

	@Mock
	private ProductRepository productRepository;
	@Mock
	private ProductCalculator productCalculator;
	@InjectMocks
	private ProductPriceService productPriceService;

	@Mock
	private Product product;
	@Captor
	private ArgumentCaptor<ProductAmount> amountCaptor;

	@Test
	void productsAreNotFoundInDB_exceptionThrown() {
		ProductPriceRequest request = new ProductPriceRequest();
		request.setId(10L);
		given(productRepository.findById(10L)).willReturn(Optional.empty());
		assertThrows(ProductIdNotFoundInDb.class, () -> productPriceService.getProductPrice(asList(request)));
	}

	@Test
	void productFoundInDb_calculatedProductsReturned() {
		ProductPriceRequest request = new ProductPriceRequest();
		request.setId(10L);
		request.setAmountOfUnits(15);
		request.setAmountOfCartons(2);
		given(productRepository.findById(10L)).willReturn(Optional.of(product));
		given(productCalculator.calculateProduct(eq(product), amountCaptor.capture())).willReturn(createCalculatedProduct());

		List<ProductPriceResponse> result = productPriceService.getProductPrice(asList(request));
		assertThat(result).isNotEmpty();
		assertThat(result.get(0))
				.returns(PRODUCT_ID, ProductPriceResponse::getId)
				.returns(PRODUCT_NAME, ProductPriceResponse::getName)
				.returns(CALCULATED_AMOUNT_OF_UNITS, ProductPriceResponse::getAmountOfUnits)
				.returns(CALCULATED_AMOUNT_OD_CARTONS, ProductPriceResponse::getAmountOfCartons)
				.returns(PRICE, ProductPriceResponse::getPrice);
	}

	private CalculatedProduct createCalculatedProduct() {
		Product product = new Product();
		product.setId(PRODUCT_ID);
		product.setName(PRODUCT_NAME);
		return new CalculatedProduct(
				product,
				new ProductAmount(CALCULATED_AMOUNT_OF_UNITS, CALCULATED_AMOUNT_OD_CARTONS),
				PRICE);
	}

}