package com.alura.apiforohub.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
@Getter
@NoArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String categoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "curso",fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Topico> topico=new ArrayList<>();

    public Curso(DatosRegistroCurso curso) {
        this.nombre= curso.nombre();
        this.categoria= curso.categoria();
    }


    public void addTopico(Topico topico) {
        this.topico.add(topico);
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", topico=" + topico +
                '}';
    }

}
