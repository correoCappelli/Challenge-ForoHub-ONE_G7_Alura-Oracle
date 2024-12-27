package com.alura.apiforohub.models;

import com.alura.apiforohub.repositorios.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "autores")
@Getter
@NoArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String correoElectronico;
    private String contrasenia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "autor",fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Topico> topicos=new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "autor",fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Respuesta> respuestas =new ArrayList<>();



    public Usuario(DatosRegistroUsuario autor) {
        this.nombre= autor.nombre();
        this.correoElectronico= autor.correoElectronico();
        this.contrasenia=autor.contrasenia();
    }


    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void addTopico(Topico topico) {
        this.topicos.add(topico);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", topicos=" + topicos +
                '}';
    }

    public void addRespuesta(Respuesta nuevaRespuesta) {
    this.respuestas.add(nuevaRespuesta);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return contrasenia;
    }

    @Override
    public String getUsername() {
        return correoElectronico;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
