package com.alura.apiforohub.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull
        Long id,
        String titulo,
        String mensaje,
        String fechaCreacion,
        String status,
        DatosRegistroCurso curso

) {
}
