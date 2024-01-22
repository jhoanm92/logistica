package com.logistica.api.exception;

public class ModeloNotFoundException extends RuntimeException{
    public ModeloNotFoundException(String mensaje){
        super(mensaje);
    }
}