package com.alura.apiforohub.repositorios;



import com.alura.apiforohub.models.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class TopicoRepositoryTest {
    @Autowired
    TopicoRepository topicoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    CursoRepository cursoRepository;


    @Test
    @DisplayName("crea un  nuevo topico completo y luego hace la busqueda del mismo")
    public void testSalvarYBuscarUnTopicoDePrueba(){

        DatosRegistroUsuario usuarioTest=new DatosRegistroUsuario(0,"usuario Test","test@gmail.com","testcontasenia");
        Usuario usuarioTestEntity=new Usuario(usuarioTest);
        DatosRegistroCurso cursoTest=new DatosRegistroCurso(0,"curso Test","categoria Test");
        Curso cursoTestEntity=new Curso(cursoTest);
        DatosRegistroTopico topicoTest = new DatosRegistroTopico(0,"topico test","mensaje test","22/22/22","ACTIVO",usuarioTest,cursoTest);
        Topico topico=new Topico(topicoTest);

        usuarioTestEntity.addTopico(topico);
        topico.setAutor(usuarioTestEntity);

        //var psw=usuarioTestEntity.getContrasenia();
        //psw=passwordEncoder.encode(psw);
        //usuarioTestEntity.setContrasenia();

        usuarioRepository.save(usuarioTestEntity);
        topicoRepository.save(topico);

        cursoTestEntity.addTopico(topico);
        topico.setCurso(cursoTestEntity);
        cursoRepository.save(cursoTestEntity);
        Topico topicoSalvado=topicoRepository.save(topico);

        assertThat(topicoSalvado).isNotNull();
        assertThat(topicoSalvado.getId()).isNotNull();

        Topico topicoBuscar=topicoRepository.findById(topicoSalvado.getId())
                .orElseThrow(()->new EntityNotFoundException());

        assertThat(topicoBuscar).isNotNull();
        assertThat(topicoBuscar.getTitulo()).isEqualTo("topico test");
        assertThat(topicoBuscar.getMensaje()).isEqualTo("mensaje test");
    }
}
