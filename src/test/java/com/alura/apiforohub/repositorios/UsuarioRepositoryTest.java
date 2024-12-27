package com.alura.apiforohub.repositorios;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.alura.apiforohub.models.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)


class UsuarioRepositoryTest {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Test
    @DisplayName("comprueba que se creo el usuario admin@gmail.com que se utiliza para obtener el Token en la ruta de LOGIN")
    public void testVerSiSeCreaElUsuarioAdminParaLosLogin(){

        Usuario admin= (Usuario) usuarioRepository.findUsuarioByCorreoElectronico("admin@gmail.com");
        assertNotNull(admin,"el administrador admin@gmail.com tiene que existir");

    }
}
