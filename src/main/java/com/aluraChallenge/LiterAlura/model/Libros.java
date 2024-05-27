package com.aluraChallenge.LiterAlura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Entity
@Table(name="libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private Double descargas;
    private String idioma;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Libros_Autores",
            joinColumns = @JoinColumn(name = "libros_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "autores_id",referencedColumnName = "id")
    )
    private List<Autores> autores;

    public Libros(){}

    public Libros(Libro libro){
        this.titulo = libro.titulo();
        this.descargas = libro.descargas();
        this.idioma = libro.idioma().get(0);
        this.autores = libro.autores().stream()
                .map(Autores::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return Id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Double getDescargas() {
        return descargas;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setAutores(List<Autores> autores) {
        this.autores = autores;
    }

    public List<Autores> getAutores() {
        return autores;
    }

    @Override
    public String toString() {
        List<String> autoresName = autores.stream().map(Autores::getNombre).toList();
        return "{\n" +
                "titulo: " + titulo + '\n' +
                "descargas:" + descargas + '\n' +
                "idioma:" + idioma + '\n' +
                "autores:" + autoresName + '\n' +
                '}';
    }
}
