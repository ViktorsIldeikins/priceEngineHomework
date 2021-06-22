package com.norwegian.price.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Product {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private int unitsInCarton;
	private BigDecimal costOfCarton;

}
