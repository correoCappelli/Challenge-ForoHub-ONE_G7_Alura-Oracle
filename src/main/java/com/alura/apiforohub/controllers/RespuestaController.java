package com.alura.apiforohub.controllers;

import com.alura.apiforohub.models.*;
import com.alura.apiforohub.repositorios.CursoRepository;
import com.alura.apiforohub.repositorios.RespuestaRepository;
import com.alura.apiforohub.repositorios.TopicoRepository;
import com.alura.apiforohub.repositorios.UsuarioRepository;
import com.alura.apiforohub.validaciones.IValidadorDeRespuestas;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/forohub")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    @Autowired
    private TopicoRepository topicoRepositorio;
    @Autowired
    private UsuarioRepository usuarioRepositorio;
    @Autowired
    private CursoRepository cursoRepositorio;
    @Autowired
    private RespuestaRepository respuestaRepositorio;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private List<IValidadorDeRespuestas> validadorDeRespuestas;

    @Transactional
    public void existeAutorTopicoYSalvar(DatosRegistroTopico topico,Topico topicoNuevo) {
        Usuario usuarioExiste = usuarioRepositorio.autorPorCorreoElectronico(topico.autor().correoElectronico());
        if (usuarioExiste != null) {
            usuarioExiste.addTopico(topicoNuevo);
            topicoNuevo.setAutor(usuarioExiste);
            topicoRepositorio.save(topicoNuevo);
        } else {
            Usuario usuarioTopico = new Usuario(topico.autor());
            usuarioTopico.addTopico(topicoNuevo);
            topicoNuevo.setAutor(usuarioTopico);
            usuarioRepositorio.save(usuarioTopico);
            topicoRepositorio.save(topicoNuevo);
        }
    }

    @Transactional
    public void existeUsuarioRespuestaYAgregar(DatosRegistroRespuestaATopico respuesta,Respuesta resp) {
        Usuario ur = usuarioRepositorio.autorPorCorreoElectronico(respuesta.autor().correoElectronico());
        if (ur != null) {
            resp.setAutor(ur);
            ur.addRespuesta(resp);
        } else {
            Usuario usuarioResp = new Usuario(respuesta.autor());
            resp.setAutor(usuarioResp);
            usuarioResp.addRespuesta(resp);
            var psw=usuarioResp.getContrasenia();
            psw=passwordEncoder.encode(psw);
            usuarioResp.setContrasenia(psw);
            usuarioRepositorio.save(usuarioResp);
        }
    }

    @PostMapping("/respuestas/{idTopico}")
    @Transactional
    public ResponseEntity<DatosListadoRespuesta> agregarRespuestaATopico(@PathVariable Long idTopico, @RequestBody @Valid DatosRegistroRespuestaATopico respuesta, UriComponentsBuilder uriComponentsBuilder){

        validadorDeRespuestas.forEach(v->v.validar(respuesta,idTopico));

        Topico topico=topicoRepositorio.findById(idTopico)
                .orElseThrow(()->new EntityNotFoundException());

        Respuesta nuevaRespuesta= new Respuesta();

        nuevaRespuesta.setFechaDeCreacion(respuesta.fechaDeCreacion());
        nuevaRespuesta.setSolucion(respuesta.solucion());
        nuevaRespuesta.setTopico(topico);

        topico.addRespuesta(nuevaRespuesta);

        existeUsuarioRespuestaYAgregar(respuesta,nuevaRespuesta);

        topicoRepositorio.save(topico);
        respuestaRepositorio.save(nuevaRespuesta);

        URI url=uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(nuevaRespuesta.getId()).toUri();
        Respuesta resppuestaRetornar=respuestaRepositorio.save(nuevaRespuesta);
        DatosListadoRespuesta datosListadoRespuesta=new DatosListadoRespuesta(resppuestaRetornar);
        return ResponseEntity.created(url).body(datosListadoRespuesta);

    }
}

