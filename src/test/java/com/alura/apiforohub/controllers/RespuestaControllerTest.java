package com.alura.apiforohub.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class RespuestaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("test que prueba a postear una respuesta vacia . Tiene que devolver BAD_REQUEST 400")
    @WithMockUser
    void agregarRespuestaATopico() throws Exception {

        var response= mvc.perform(MockMvcRequestBuilders.post("/forohub/respuestas/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }
}