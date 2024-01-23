package com.oh.api.repository;

import com.oh.api.model.Cliente;
import com.oh.api.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByDate(Date date);

}
