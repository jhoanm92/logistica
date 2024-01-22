package com.logistica.api.controller;

import com.logistica.api.entity.LugarEntrega;
import com.logistica.api.service.LugarEntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("lugar-entrega")
public class LugarEntregaController {

    @Autowired
    private LugarEntregaService service;

    @GetMapping
    public ResponseEntity<List<LugarEntrega>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }
}
