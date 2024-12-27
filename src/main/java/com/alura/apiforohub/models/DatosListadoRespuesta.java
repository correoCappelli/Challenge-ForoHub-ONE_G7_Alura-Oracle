package com.alura.apiforohub.models;

import jakarta.persistence.*;

public record DatosListadoRespuesta(

        Long id,
        String topico,
        String fechaDeCreacion,
        String autor,
        String mailAutor,
        String solucion
) {

    public DatosListadoRespuesta (Respuesta respuesta){
        this(
                respuesta.getId(),
                respuesta.getTopico().getTitulo(),
                respuesta.getFechaDeCreacion(),
                respuesta.getAutor().getNombre(),
                respuesta.getAutor().getCorreoElectronico(),
                respuesta.getSolucion()
        );
    }
}
