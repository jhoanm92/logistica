package com.logistica.api.service.impl;

import com.logistica.api.entity.Envio;
import com.logistica.api.exception.ModeloNotFoundException;
import com.logistica.api.repository.EnvioRepository;
import com.logistica.api.service.EnvioService;
import com.logistica.api.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class EnvioServiceImpl implements EnvioService {

    @Autowired
    private EnvioRepository repository;

    @Override
    public List<Envio> obtenerTodos(){
        return repository.findAll();
    }

    @Override
    public Envio guardar(Envio envio) {

        envio.setPlacaFlota(this.generarPlacaFlota(envio.getTipoEnvio().getId()));
        envio.setNumeroGuia(this.generarNumeroGuia());
        envio = this.descuento(envio);

        return repository.save(envio);
    }

    @Override
    public Envio actualizar(Envio envio) {
        this.obtenerPorId(envio.getId());
        return repository.save(envio);
    }

    @Override
    public Envio obtenerPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ModeloNotFoundException("ID NO ENCONTRADO " + id));
    }

    @Override
    public void eliminar(Integer id) {
        this.obtenerPorId(id);
        repository.deleteById(id);
    }

    private String generarPlacaFlota(int id){
        Random random = new Random();
        StringBuilder sb;

        do {
            sb = new StringBuilder();
            if(id == Constantes.ID_ENVIOS_TERRESTRES) { //Envio Terrestre
                for (int i = 0; i < 3; i++) {
                    sb.append((char)(random.nextInt(26) + 'a'));
                }
                sb.append(random.nextInt(900) + 100);
            }

            if(id == Constantes.ID_ENVIOS_MARITIMOS) { //Envio Maritimos
                for (int i = 0; i < 3; i++) {
                    sb.append((char)(random.nextInt(26) + 'a'));
                }
                sb.append(random.nextInt(9000) + 1000);
                sb.append((char)(random.nextInt(26) + 'a'));
            }

        }while (repository.existsByPlacaFlotaIgnoreCase(sb.toString()));

        return sb.toString();
    }

    private Long generarNumeroGuia() {
        Random random = new Random();
        Long numeroGuia;

        do {
            numeroGuia = random.nextLong(9000000000L) + 1000000000;
        }while (repository.existsByNumeroGuia(numeroGuia));

        return numeroGuia;
    }

    private Envio descuento(Envio envio) {
        
        if ( envio.getValorEnvio().equals(BigDecimal.valueOf(0)) || envio.getCantidad() <= Constantes.CANTIDAD_PRODUCTO ) {
            envio.setValorNeto( envio.getValorEnvio() );
            envio.setDescuento( BigDecimal.valueOf(0) );
            envio.setValorEnvio( envio.getValorEnvio() );
            return envio;
        }

        if ( envio.getTipoEnvio().getId() == Constantes.ID_ENVIOS_TERRESTRES ) { //Terrestre
            envio.setValorNeto( envio.getValorEnvio() );
            envio.setDescuento( envio.getValorEnvio().multiply(Constantes.DESCUENTO_5_PORCIENTO) );
            envio.setValorEnvio( envio.getValorEnvio().multiply(Constantes.VALOR_95_PORCIENTO) );
        }

        if ( envio.getTipoEnvio().getId() == Constantes.ID_ENVIOS_MARITIMOS ) { //Maritimo
            envio.setValorNeto( envio.getValorEnvio() );
            envio.setDescuento( envio.getValorEnvio().multiply(Constantes.DESCUENTO_3_PORCIENTO) );
            envio.setValorEnvio( envio.getValorEnvio().multiply(Constantes.VALOR_97_PORCIENTO) );
        }

        return envio;
    }
}
