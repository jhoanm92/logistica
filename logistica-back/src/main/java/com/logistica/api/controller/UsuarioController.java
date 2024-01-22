package com.logistica.api.controller;

import com.logistica.api.entity.Usuario;
import com.logistica.api.service.UsuarioService;
import com.logistica.api.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<String> guardar(@RequestBody Usuario usuario) {
        service.registrarEncriptado(usuario);
        return new ResponseEntity<> (Constantes.USUARIO_CREADO, HttpStatus.CREATED);
    }
}
