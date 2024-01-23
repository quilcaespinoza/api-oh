package com.oh.api.repository;

import com.oh.api.model.Cliente;
import com.oh.api.model.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {
}
