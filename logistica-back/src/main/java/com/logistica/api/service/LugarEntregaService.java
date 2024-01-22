package com.logistica.api.service;

import com.logistica.api.entity.Cliente;
import com.logistica.api.entity.LugarEntrega;

import java.util.List;

public interface LugarEntregaService {

    List<LugarEntrega> obtenerTodos();
}
