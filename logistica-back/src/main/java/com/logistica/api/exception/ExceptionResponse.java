package com.logistica.api.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionResponse {
    private LocalDateTime fecha;
    private String mensaje;
    private String detalle;

    public ExceptionResponse(LocalDateTime fecha, String mensaje, String detalle) {
        this.fecha = fecha;
        this.mensaje = mensaje;
        this.detalle = detalle;
    }
}