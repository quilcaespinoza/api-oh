package com.oh.api.controller;

import com.oh.api.Constants;
import com.oh.api.model.Product;
import com.oh.api.model.Sale;
import com.oh.api.model.SaleDetail;
import com.oh.api.repository.SaleDetailRepository;
import com.oh.api.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sale")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SaleController {
    @Autowired
    SaleRepository saleRepository;
    @Autowired
    SaleDetailRepository saleDetailRepository;

    @PostMapping(produces = Constants.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> SaleCreate(@RequestBody Map<String, Object> request) {
        try {
            // Extraer la información de la venta y el detalle de la solicitud
            Map<String, Object> saleMap = (Map<String, Object>) request.get("sale");
            Map<String, Object> saleDetailMap = (Map<String, Object>) request.get("saleDetail");

            // Mapear la información de la venta
            Sale sale = new Sale();
            sale.setIdClient((Integer) saleMap.get("idClient"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormat.parse((String) saleMap.get("date"));
            sale.setDate(date);

            Sale savedSale = saleRepository.save(sale);

            SaleDetail saleDetail = new SaleDetail();
            saleDetail.setIdSale( savedSale.getId().intValue());
            saleDetail.setIdProduct((Integer) saleDetailMap.get("idProduct"));
            saleDetail.setAmount((Integer) saleDetailMap.get("amount"));

            // Guardar el detalle de venta
            saleDetailRepository.save(saleDetail);

            return new ResponseEntity<>("Venta y detalle de venta creados con éxito", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Error al crear la venta y detalle de venta", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/by-date", produces = Constants.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Sale>> getSalesByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        List<Sale> sales = null;
        try {
             sales = saleRepository.findByDate(date);

            return new ResponseEntity<>(sales, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(sales, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
