package com.alura.apiforohub.repositorios;

import com.alura.apiforohub.models.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RespuestaRepository extends JpaRepository<Respuesta,Long> {
//    @Query("SELECT COUNT(r) FROM Respuesta r WHERE r.autor.id = :autorId AND r.topico.id = :topicoId")
//    Long cantidadDeRespuestasPorAutorYTopico(@Param("autorId") Long autorId, @Param("topicoId") Long topicoId);



    @Query(value = "SELECT COUNT(*) FROM respuestas WHERE autor_id = :autorId AND topico_id = :topicoId", nativeQuery = true)
    Long cantidadDeRespuestasPorAutorYTopico(@Param("autorId") Long autorId, @Param("topicoId") Long topicoId);
}
