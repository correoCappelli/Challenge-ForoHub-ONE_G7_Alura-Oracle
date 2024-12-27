package com.alura.apiforohub.seguridad;

import com.alura.apiforohub.repositorios.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("filtro activado");
        //llama al siguiente filtro en la cadena
        //VERSION 1
//        var tokenRequest=request.getHeader("Authorization");
//        if (tokenRequest==null || tokenRequest==""){
//            throw new RuntimeException("el token no es valido");
//        }
//        tokenRequest=tokenRequest.replace("Bearer ","");
//        System.out.println(tokenRequest);
//        System.out.println(tokenService.getSubject(tokenRequest));
//        filterChain.doFilter(request,response);

        //VERSION 2 para que no de error el login si no tiene token
        var tokenRequest=request.getHeader("Authorization");
        if (tokenRequest!=null){
            tokenRequest=tokenRequest.replace("Bearer ","");
            var subject=tokenService.getSubject(tokenRequest);
            if(subject!=null){
                var usuario=usuarioRepository.findUsuarioByCorreoElectronico(subject);
                var authentication=new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        filterChain.doFilter(request,response);
    }
}
