package com.alura.apiforohub.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

public record DatosRegistroRespuesta(
        int id,
        @NotNull
        String topico,
        @NotBlank
        @Pattern(regexp = "\\d{2}/\\d{2}/\\d{2}", message = "la fecha es con formato DD/MM/YY")
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        String fechaDeCreacion,
        @NotNull
        @Valid
        DatosRegistroUsuario autor,
        @NotBlank
        String solucion
) {
}
