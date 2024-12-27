package com.alura.apiforohub.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topicos")
@Getter
@Setter
@NoArgsConstructor
public class Topico {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private  String fechaCreacion;
    @Enumerated(EnumType.STRING)
    private StatusTopico status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topico",fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Respuesta> respuestas = new ArrayList<>();

    public Topico(DatosRegistroTopico topico) {
        this.titulo=topico.titulo();
        this.mensaje= topico.mensaje();
        this.fechaCreacion= topico.fechaCreacion();
        this.status= StatusTopico.textoEspañol(topico.status());
    }

    public void addRespuesta(Respuesta respuesta) {
        this.respuestas.add(respuesta);
    }

    public void actualizarDatosTopico(DatosActualizarTopico datosActualizarTopico) {
        if(datosActualizarTopico.titulo()!=null) {
            this.titulo = datosActualizarTopico.titulo();
        }
        if(datosActualizarTopico.mensaje()!=null) {
            this.mensaje = datosActualizarTopico.mensaje();
        }
        if(datosActualizarTopico.fechaCreacion()!=null) {
            this.fechaCreacion = datosActualizarTopico.fechaCreacion();
        }
        if(datosActualizarTopico.status()!=null) {
            this.status = StatusTopico.textoEspañol(datosActualizarTopico.status());
        }
    }
    //private List<DatosRegistroRespuesta> respuestas;


    @Override
    public String toString() {
        return "Topico{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", status='" + status + '\'' +
                ", autor=" + autor +
                ", curso=" + curso +
                '}';
    }
}
