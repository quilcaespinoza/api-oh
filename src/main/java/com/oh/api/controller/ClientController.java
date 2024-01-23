package com.oh.api.controller;

import com.oh.api.Constants;
import com.oh.api.model.Cliente;
import com.oh.api.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;

@RestController
@RequestMapping("client")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClientController {
    @Autowired
    ClientRepository clientRepository;

    @PostMapping(produces = Constants.APPLICATION_JSON_VALUE)
    public Cliente clientCreate(@RequestBody Cliente cliente) {
        return clientRepository.save(cliente);
    }

    @GetMapping(produces = Constants.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cliente>> obtenerTodosLosClientes() {
        List<Cliente> clientes = clientRepository.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
}
