package com.alura.apiforohub.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component

public class Argumentos{

    @Value("${argumentoUno:defaultValue}")
    public String argumentoUno;

    public void printArgument()
    {
        System.out.println("Probando argumentoUno : " + argumentoUno);
    }
}
