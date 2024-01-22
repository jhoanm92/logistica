package com.logistica.api.service.impl;

import com.logistica.api.entity.Usuario;
import com.logistica.api.repository.UsuarioRepository;
import com.logistica.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    public Usuario registrarEncriptado(Usuario usuario){
        usuario.setPassword(bcrypt.encode(usuario.getPassword()));
        return repository.save(usuario);
    }
}
