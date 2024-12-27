package com.alura.apiforohub.models;

import java.io.DataOutput;
import java.util.List;

public record DatosListadoTopicosYRespuestas(
        Long id,
        String titulo,
        String mensaje,
        String fechaCreacion,
        StatusTopico status,
        String autor,
        String curso,
        List<String> respuesta
) {
    public DatosListadoTopicosYRespuestas( Topico topico) {
        this(
        topico.getId(),
        topico.getTitulo(),
        topico.getMensaje(),
        topico.getFechaCreacion(),
        topico.getStatus(),
        topico.getAutor().getNombre(),
        topico.getCurso().getNombre(),
        topico.getRespuestas().stream().map(l->l.getAutor().getNombre()
                +"  "+l.getAutor().getCorreoElectronico()
                +"  "+l.getSolucion()).toList());
    }


}
