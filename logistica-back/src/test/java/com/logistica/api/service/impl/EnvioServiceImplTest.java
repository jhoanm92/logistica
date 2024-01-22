package com.logistica.api.service.impl;

import com.logistica.api.entity.Cliente;
import com.logistica.api.entity.Envio;
import com.logistica.api.entity.LugarEntrega;
import com.logistica.api.entity.TipoEnvio;
import com.logistica.api.repository.EnvioRepository;
import com.logistica.api.util.Constantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EnvioServiceImplTest {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioServiceImpl envioService;

    @Captor
    private ArgumentCaptor<Envio> envioCaptor;

    Envio envio = new Envio();

    BigDecimal valorEnvio = BigDecimal.valueOf(1000);

    @BeforeEach
    public void inicializar() {
        LugarEntrega lugarEntrega = new LugarEntrega();

        TipoEnvio tipoEnvio = new TipoEnvio();
        tipoEnvio.setId(Constantes.ID_ENVIOS_TERRESTRES);

        Cliente cliente = new Cliente();

        envio.setTipoPorducto("Juguetes");
        envio.setCantidad(10);
        envio.setFechaRegistro(LocalDateTime.now());
        envio.setFechaEntrega(LocalDateTime.now());
        envio.setValorEnvio(valorEnvio);
        envio.setLugarEntrega(lugarEntrega);
        envio.setTipoEnvio(tipoEnvio);
        envio.setCliente(cliente);
    }


    @Test
    void guardar_exitoso_sin_descuento() {
        BigDecimal valorEnvioEsperado = valorEnvio;
        BigDecimal valorDescuentoEsperado = BigDecimal.valueOf(0);
        BigDecimal valorNetoEsperado = valorEnvio;

        when(envioRepository.existsByNumeroGuia(any())).thenReturn(false);
        when(envioRepository.existsByPlacaFlotaIgnoreCase(any())).thenReturn(false);
        when(envioRepository.save(envioCaptor.capture())).thenReturn(envio);

        Envio result = envioService.guardar(envio);
        Envio envioCapturado = envioCaptor.getValue();

        assertEquals(valorEnvioEsperado, envioCapturado.getValorEnvio());
        assertEquals(valorDescuentoEsperado, envioCapturado.getDescuento());
        assertEquals(valorNetoEsperado, envioCapturado.getValorNeto());
    }

    @Test
    void guardar_exitoso_con_descuento_terrestre() {
        BigDecimal valorEnvioEsperado = BigDecimal.valueOf(95000,2);
        BigDecimal valorDescuentoEsperado = BigDecimal.valueOf(5000,2);
        BigDecimal valorNetoEsperado = valorEnvio;

        TipoEnvio tipoEnvio = new TipoEnvio();
        tipoEnvio.setId(Constantes.ID_ENVIOS_TERRESTRES);

        when(envioRepository.existsByNumeroGuia(any())).thenReturn(false);
        when(envioRepository.existsByPlacaFlotaIgnoreCase(any())).thenReturn(false);
        when(envioRepository.save(envioCaptor.capture())).thenReturn(envio);
        envio.setCantidad(11);
        envio.setTipoEnvio(tipoEnvio);

        Envio result = envioService.guardar(envio);
        Envio envioCapturado = envioCaptor.getValue();

        assertEquals(valorEnvioEsperado, envioCapturado.getValorEnvio());
        assertEquals(valorDescuentoEsperado, envioCapturado.getDescuento());
        assertEquals(valorNetoEsperado, envioCapturado.getValorNeto());
    }

    @Test
    void guardar_exitoso_con_descuento_maritimo() {
        BigDecimal valorEnvioEsperado = BigDecimal.valueOf(97000,2);
        BigDecimal valorDescuentoEsperado = BigDecimal.valueOf(3000,2);
        BigDecimal valorNetoEsperado = valorEnvio;

        TipoEnvio tipoEnvio = new TipoEnvio();
        tipoEnvio.setId(Constantes.ID_ENVIOS_MARITIMOS);

        when(envioRepository.existsByNumeroGuia(any())).thenReturn(false);
        when(envioRepository.existsByPlacaFlotaIgnoreCase(any())).thenReturn(false);
        when(envioRepository.save(envioCaptor.capture())).thenReturn(envio);
        envio.setCantidad(11);
        envio.setTipoEnvio(tipoEnvio);

        Envio result = envioService.guardar(envio);
        Envio envioCapturado = envioCaptor.getValue();

        assertEquals(valorEnvioEsperado, envioCapturado.getValorEnvio());
        assertEquals(valorDescuentoEsperado, envioCapturado.getDescuento());
        assertEquals(valorNetoEsperado, envioCapturado.getValorNeto());
    }

    @Test
    void guardar_exitoso_con_numeroGuia() {
        Integer largoNumeroGuia = 10;

        when(envioRepository.existsByNumeroGuia(any())).thenReturn(false);
        when(envioRepository.existsByPlacaFlotaIgnoreCase(any())).thenReturn(false);
        when(envioRepository.save(envioCaptor.capture())).thenReturn(envio);

        Envio result = envioService.guardar(envio);
        Envio envioCaturado = envioCaptor.getValue();

        assertEquals(largoNumeroGuia, String.valueOf(envioCaturado.getNumeroGuia()).length());
    }

    @Test
    void guardar_exitoso_con_placaFlota_terrestre() {
        Integer largoPlacaFlota = 6;

        TipoEnvio tipoEnvio = new TipoEnvio();
        tipoEnvio.setId(Constantes.ID_ENVIOS_TERRESTRES);

        when(envioRepository.existsByNumeroGuia(any())).thenReturn(false);
        when(envioRepository.existsByPlacaFlotaIgnoreCase(any())).thenReturn(false);
        when(envioRepository.save(envioCaptor.capture())).thenReturn(envio);
        envio.setTipoEnvio(tipoEnvio);

        Envio result = envioService.guardar(envio);
        Envio envioCaturado = envioCaptor.getValue();

        String placaFlota = envioCaturado.getPlacaFlota();

        String letras = placaFlota.substring(0, 3);
        Integer numeros = Integer.parseInt(placaFlota.substring(3, 6));

        assertEquals(largoPlacaFlota, envioCaturado.getPlacaFlota().length());
        assertEquals(true, letras instanceof String);
        assertEquals(true, numeros instanceof Integer);
    }

    @Test
    void guardar_exitoso_con_placaFlota_maritima() {
        Integer largoPlacaFlota = 8;

        TipoEnvio tipoEnvio = new TipoEnvio();
        tipoEnvio.setId(Constantes.ID_ENVIOS_MARITIMOS);

        when(envioRepository.existsByNumeroGuia(any())).thenReturn(false);
        when(envioRepository.existsByPlacaFlotaIgnoreCase(any())).thenReturn(false);
        when(envioRepository.save(envioCaptor.capture())).thenReturn(envio);
        envio.setTipoEnvio(tipoEnvio);

        Envio result = envioService.guardar(envio);
        Envio envioCaturado = envioCaptor.getValue();

        String placaFlota = envioCaturado.getPlacaFlota();

        String letras = placaFlota.substring(0, 3);
        Integer numeros = Integer.parseInt(placaFlota.substring(3, 7));
        String ultimaLetra = placaFlota.substring(7, 8);

        assertEquals(largoPlacaFlota, envioCaturado.getPlacaFlota().length());
        assertEquals(true, letras instanceof String);
        assertEquals(true, numeros instanceof Integer);
        assertEquals(true, ultimaLetra instanceof String);
    }
}