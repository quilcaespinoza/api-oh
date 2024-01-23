package com.oh.api.controller;

import com.oh.api.Constants;
import com.oh.api.model.Cliente;
import com.oh.api.model.Product;
import com.oh.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@RestController
@RequestMapping("product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @PostMapping(produces = Constants.APPLICATION_JSON_VALUE)
    public Product productCreate(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping(value = "/{productId}", produces = Constants.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody Product productoActualizado) {
        try {
            if (!productRepository.existsById(productId)) {
                return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
            }

            Product productoExistente = productRepository.findById(productId).orElse(null);

            if (productoExistente != null) {
                productoExistente.setName(productoActualizado.getName());
                productoExistente.setPrice(productoActualizado.getPrice());

                productRepository.save(productoExistente);

                return new ResponseEntity<>("Producto actualizado con éxito", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Error al actualizar el producto", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el producto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/list", produces = Constants.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Product>> getProductPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productsPage = productRepository.findAll(pageable);

        return new ResponseEntity<>(productsPage, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{productId}", produces = Constants.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        try {
            if (!productRepository.existsById(productId)) {
                return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
            }

            productRepository.deleteById(productId);

            return new ResponseEntity<>("Producto eliminado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el producto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
