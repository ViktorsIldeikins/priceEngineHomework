package com.norwegian.price.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.norwegian.price.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
