package com.logistica.api.service.impl;

import com.logistica.api.entity.Cliente;
import com.logistica.api.exception.ModeloNotFoundException;
import com.logistica.api.repository.ClienteRepository;
import com.logistica.api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public List<Cliente> obtenerTodos(){
        return repository.findAll();
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public Cliente actualizar(Cliente cliente) {
        this.obtenerPorId(cliente.getId());
        return repository.save(cliente);
    }

    @Override
    public Cliente obtenerPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ModeloNotFoundException("ID NO ENCONTRADO" + id));
    }

    @Override
    public void eliminar(Integer id) {
        this.obtenerPorId(id);
        repository.deleteById(id);
    }
}
