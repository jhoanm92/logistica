package com.logistica.api.service;

import com.logistica.api.entity.Cliente;
import com.logistica.api.entity.TipoEnvio;

import java.util.List;

public interface TipoEnvioService {

    List<TipoEnvio> obtenerTodos();
}
