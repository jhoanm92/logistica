package com.logistica.api.controller;

import com.logistica.api.entity.TipoEnvio;
import com.logistica.api.service.TipoEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tipo-envio")
public class TipoEnvioController {

    @Autowired
    private TipoEnvioService service;

    @GetMapping
    public ResponseEntity<List<TipoEnvio>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }
}
