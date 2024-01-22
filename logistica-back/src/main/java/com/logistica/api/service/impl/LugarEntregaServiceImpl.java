package com.logistica.api.service.impl;

import com.logistica.api.entity.LugarEntrega;
import com.logistica.api.repository.LugarEntregaRepository;
import com.logistica.api.service.LugarEntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LugarEntregaServiceImpl implements LugarEntregaService {

    @Autowired
    private LugarEntregaRepository repository;

    @Override
    public List<LugarEntrega> obtenerTodos(){
        return repository.findAll();
    }
}
