package com.alura.apiforohub.validaciones;

import com.alura.apiforohub.models.DatosRegistroRespuestaATopico;
import com.alura.apiforohub.models.DatosRegistroUsuario;
import com.alura.apiforohub.models.Usuario;
import com.alura.apiforohub.repositorios.RespuestaRepository;
import com.alura.apiforohub.repositorios.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidacionMasDeDosRespuestasPorTopicoPorAutor implements IValidadorDeRespuestas {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RespuestaRepository respuestaRepository;

    public void validar(DatosRegistroRespuestaATopico datos,Long idTopico){
        var contadorRespuestasAlTopico=0;
        var emailUsuarioQueCreaLaRespuesta=datos.autor().correoElectronico();
        var autorQueCreaLaRespuesta=
                usuarioRepository.autorPorCorreoElectronicoOptional(emailUsuarioQueCreaLaRespuesta)
                        .orElseThrow(()->new EntityNotFoundException());

        var cantidadDeRespuestas=respuestaRepository.cantidadDeRespuestasPorAutorYTopico(autorQueCreaLaRespuesta.getId(),idTopico);

        if(cantidadDeRespuestas>2){
            throw new ValidacionExcepcion("no se puede responder mas de dos veces al topico");

        }
    }
}
