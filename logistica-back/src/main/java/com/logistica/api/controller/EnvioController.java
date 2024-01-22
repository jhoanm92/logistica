package com.logistica.api.controller;

import com.logistica.api.entity.Envio;
import com.logistica.api.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("envio")
public class EnvioController {

    @Autowired
    private EnvioService service;

    @GetMapping
    public ResponseEntity<List<Envio>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<Envio> guardar(@RequestBody Envio envio) {        
        return new ResponseEntity<> (service.guardar(envio), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<Envio> actualizar(@RequestBody Envio envio) {
        return ResponseEntity.ok(service.actualizar(envio));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
