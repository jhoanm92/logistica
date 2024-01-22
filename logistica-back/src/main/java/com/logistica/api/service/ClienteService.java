package com.logistica.api.service;

import com.logistica.api.entity.Cliente;

import java.util.List;

public interface ClienteService {

    List<Cliente> obtenerTodos();
    Cliente guardar (Cliente cliente);

    Cliente actualizar (Cliente cliente);

    Cliente obtenerPorId (Integer id);

    void eliminar (Integer id);
}
