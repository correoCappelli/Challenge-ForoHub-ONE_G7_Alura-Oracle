package com.alura.apiforohub.repositorios;

import com.alura.apiforohub.models.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico,Long> {
        @Query("SELECT t FROM Topico t LEFT JOIN FETCH t.respuestas")
        List<Topico> buscarTopicosYRespuestas();

}
