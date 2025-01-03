package com.alura.apiforohub.controllers;

import com.alura.apiforohub.models.*;
import com.alura.apiforohub.repositorios.CursoRepository;
import com.alura.apiforohub.repositorios.TopicoRepository;
import com.alura.apiforohub.repositorios.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forohub")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepositorio;
    @Autowired
    private UsuarioRepository usuarioRepositorio;
    @Autowired
    private CursoRepository cursoRepositorio;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
            var psw=usuarioTopico.getContrasenia();
            psw=passwordEncoder.encode(psw);
            usuarioTopico.setContrasenia(psw);

            usuarioRepositorio.save(usuarioTopico);
            topicoRepositorio.save(topicoNuevo);
        }
    }


    @PostMapping("/topicos")
    public ResponseEntity<DatosListadoTopico> requestTopico(@RequestBody @Valid DatosRegistroTopico topico, UriComponentsBuilder uriComponentsBuilder){

        Topico nuevoTopico = new Topico(topico);

        existeAutorTopicoYSalvar(topico,nuevoTopico);

        Curso nuevoCurso = new Curso(topico.curso());
        nuevoCurso.addTopico(nuevoTopico);
        nuevoTopico.setCurso(nuevoCurso);
        cursoRepositorio.save(nuevoCurso);
        topicoRepositorio.save(nuevoTopico);

        URI url=uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(nuevoTopico.getId()).toUri();
        Topico topicoRetornar=topicoRepositorio.save(nuevoTopico);
        DatosListadoTopico datosListadoTopico=new DatosListadoTopico(topicoRetornar);
        return ResponseEntity.created(url).body(datosListadoTopico);

    }

    @GetMapping("topicos/{id}")
    public ResponseEntity<DatosListadoTopico> listarTopicoPorId(@PathVariable Long id){
        Topico topico=topicoRepositorio.findById(id)
                .orElseThrow(()->new EntityNotFoundException());
        DatosListadoTopico topicoAListar=new DatosListadoTopico(topico);
        return ResponseEntity.ok(topicoAListar);
    }

    @GetMapping("/topicos/topicos+respuestas")
    public ResponseEntity<List<DatosListadoTopicosYRespuestas>> listarTopicosYRespuestas(){
        return ResponseEntity.ok(topicoRepositorio.buscarTopicosYRespuestas().stream()
                .map(DatosListadoTopicosYRespuestas::new)
                .toList());
    }

    @GetMapping("/topicos/paginacion")
    public ResponseEntity<Page<DatosListadoTopico>> listarPaginasTopicos(Pageable paginacion){
        return ResponseEntity.ok(topicoRepositorio.findAll(paginacion).map(DatosListadoTopico::new));
    }

    @GetMapping("/topicos/primeros10topicos")
    public ResponseEntity<Page<DatosListadoTopico>> listarPrimeros10TopicosAscendente(
            @PageableDefault(size = 10,sort = "titulo",direction = Sort.Direction.ASC)
            Pageable paginacion){
            return ResponseEntity.ok(topicoRepositorio.findAll(paginacion).map(DatosListadoTopico::new));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/topicos/{id}")
    @Transactional
    public ResponseEntity actualizarTopicoPorId(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
            Topico topico = topicoRepositorio.findById(id)
                    .orElseThrow(()->new EntityNotFoundException());
            topico.actualizarDatosTopico(datosActualizarTopico);
            return ResponseEntity.ok(new DatosListadoTopico(topico));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/topicos/{id}")
    @Transactional
    public ResponseEntity borrarTopicoPorId(@PathVariable Long id) {
            Topico topico = topicoRepositorio.findById(id)
                    .orElseThrow(()->new EntityNotFoundException());
            topicoRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
    }
}
