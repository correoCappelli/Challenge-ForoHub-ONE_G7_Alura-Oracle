package com.alura.apiforohub.repositorios;

import com.alura.apiforohub.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    @Query("SELECT u FROM Usuario u WHERE u.correoElectronico ILIKE %:email%")
    Usuario autorPorCorreoElectronico(String email);
    @Query("SELECT u FROM Usuario u WHERE u.correoElectronico ILIKE %:email%")
    Optional<Usuario> autorPorCorreoElectronicoOptional(String email);
    @Query("SELECT u FROM Usuario u WHERE u.nombre ILIKE %:nombre%")
    Usuario autorPorNombre(String nombre);

    @Query("SELECT u FROM Usuario u WHERE u.correoElectronico ILIKE %:email%")
    UserDetails autorPorCorreoElectronicoDetails(String email);

    UserDetails findUsuarioByNombre(String nombre);
    UserDetails findUsuarioByCorreoElectronico(String email);

}
