package com.alura.apiforohub.validaciones;

import com.alura.apiforohub.models.DatosRegistroRespuesta;
import com.alura.apiforohub.models.DatosRegistroRespuestaATopico;
import com.alura.apiforohub.models.StatusTopico;
import com.alura.apiforohub.repositorios.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionRespuestaTopicoINACTIVO implements IValidadorDeRespuestas {

    @Autowired
    TopicoRepository topicoRepository;

    public void validar(DatosRegistroRespuestaATopico datos,Long idTopico){
        var idTopicoRespuesta=idTopico;
        var estadoDelTopico=topicoRepository.findById(idTopico).get().getStatus().equals(StatusTopico.INACTIVO);
        if(estadoDelTopico){
            throw new ValidacionExcepcion("el topico se encuentra inactivo. No se puede responder");
        }
    }
}
