package com.alura.apiforohub.validaciones;

import com.alura.apiforohub.models.DatosRegistroRespuestaATopico;

public interface IValidadorDeRespuestas {
    void validar(DatosRegistroRespuestaATopico datos,Long idTopico);
}
