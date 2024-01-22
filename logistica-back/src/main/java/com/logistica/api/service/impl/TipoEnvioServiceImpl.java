package com.logistica.api.service.impl;

import com.logistica.api.entity.TipoEnvio;
import com.logistica.api.repository.TipoEnvioRepository;
import com.logistica.api.service.TipoEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoEnvioServiceImpl implements TipoEnvioService {

    @Autowired
    private TipoEnvioRepository repository;

    @Override
    public List<TipoEnvio> obtenerTodos(){
        return repository.findAll();
    }
}
