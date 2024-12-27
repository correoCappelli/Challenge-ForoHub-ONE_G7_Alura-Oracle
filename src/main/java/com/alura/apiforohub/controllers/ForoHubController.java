package com.alura.apiforohub.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forohub")

public class ForoHubController {

    @GetMapping
public String hello(){
    System.out.println("Bienvenido al Foro Hub de Alura");
    return "Bienvenido al Foro Hub de Alura";
}
}
