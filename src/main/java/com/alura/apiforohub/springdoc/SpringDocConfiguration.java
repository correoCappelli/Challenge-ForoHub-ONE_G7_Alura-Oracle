package com.alura.apiforohub.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("ForoHub Alura ONE API REST")
                        .description("API Rest de la aplicacion ALura-Oracle-ONE ForoHub que permite crear topicos y realizar respuestas a los mismos. Se implementan tambien Usuarios con autenticacion y autorizacion")
                        .contact(new Contact()
                                .name("Alura ONE G7 Backend")
                                .email("backend@forohub.com"))
                        .license(new License()
                                .name("no especifica")
                                .url("no especifica")));
    }


}
