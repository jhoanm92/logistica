package com.logistica.api.repository;

import com.logistica.api.entity.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EnvioRepository extends JpaRepository<Envio, Integer> {
    boolean existsByPlacaFlotaIgnoreCase(String placaFlota);
    boolean existsByNumeroGuia(Long numeroGuia);


}