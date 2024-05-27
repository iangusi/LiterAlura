package com.aluraChallenge.LiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private int nacimiento;
    private int fallecimiento;
    @ManyToMany(mappedBy = "autores")
    private List<Libros> libros;

    public Autores(){}

    public Autores(Autor autor) {
        this.nombre = autor.nombre();
        this.nacimiento = autor.nacimiento();
        this.fallecimiento = autor.fallecimiento();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNacimiento() {
        return nacimiento;
    }

    public int getFallecimiento() {
        return fallecimiento;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {

        List<String> libroTitle = libros.stream().map(Libros::getTitulo).toList();
        return "{\n" +
                "nombre:" + nombre + '\n' +
                "nacimiento:" + nacimiento + '\n' +
                "fallecimiento:" + fallecimiento + '\n' +
                "libros:" + libroTitle + '\n' +
                '}';
    }
}
