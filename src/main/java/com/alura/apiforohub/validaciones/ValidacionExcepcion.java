package com.alura.apiforohub.validaciones;

public class ValidacionExcepcion extends RuntimeException {
    public ValidacionExcepcion(String mensaje) {
    super(mensaje);
    }
}
