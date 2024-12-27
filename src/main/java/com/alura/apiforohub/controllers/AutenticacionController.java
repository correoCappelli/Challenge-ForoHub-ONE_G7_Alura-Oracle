package com.alura.apiforohub.controllers;

import com.alura.apiforohub.seguridad.DatosJWTToken;
import com.alura.apiforohub.models.DatosLoginUsuario;
import com.alura.apiforohub.models.Usuario;
import com.alura.apiforohub.seguridad.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forohub")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity autenticacionUsuario(@RequestBody @Valid DatosLoginUsuario datosLoginUsuario){
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(datosLoginUsuario.correoElectronico(),datosLoginUsuario.contrasenia());
        var usuarioAutenticado=authenticationManager.authenticate(authenticationToken);
        var JwtToken=tokenService.generarToken((Usuario)usuarioAutenticado.getPrincipal());
        System.out.println(JwtToken);
        //return ResponseEntity.ok().build();
        return ResponseEntity.ok(new DatosJWTToken(JwtToken));
    }
}
