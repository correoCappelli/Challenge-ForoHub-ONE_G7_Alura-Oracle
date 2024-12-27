package com.alura.apiforohub;

import com.alura.apiforohub.models.Argumentos;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//para testear con Insomnia se deshabilita temporariamente la config de seguridad
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
public class ApiforohubApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiforohubApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

	}
}
