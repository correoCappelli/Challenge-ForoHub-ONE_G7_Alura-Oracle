package com.alura.apiforohub.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DatosListadoTopico(

        Long id,
        String titulo,
        String mensaje,
        String fechaCreacion,
        StatusTopico status,
        String autor,
        String curso


) {
    //ver constructores no canonicos y canonicos
    public DatosListadoTopico (Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre());

    }
}
