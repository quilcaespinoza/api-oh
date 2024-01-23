package com.oh.api.repository;

import com.oh.api.model.Cliente;
import com.oh.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
