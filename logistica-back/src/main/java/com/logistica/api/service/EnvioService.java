package com.logistica.api.service;

import com.logistica.api.entity.Envio;

import java.util.List;

public interface EnvioService {

    List<Envio> obtenerTodos();
    Envio guardar (Envio envio);

    Envio actualizar (Envio envio);

    Envio obtenerPorId (Integer id);

    void eliminar (Integer id);
}
