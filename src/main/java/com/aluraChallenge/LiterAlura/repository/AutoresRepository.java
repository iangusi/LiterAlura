package com.aluraChallenge.LiterAlura.repository;

import com.aluraChallenge.LiterAlura.model.Autores;
import com.aluraChallenge.LiterAlura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutoresRepository extends JpaRepository<Autores,Long> {
    @Query("SELECT l FROM Autores a join a.libros l WHERE a.id =:id_autor")
    public List<Libros> EncontrarLibros(long id_autor);

    @Query("SELECT a FROM Autores a WHERE a.nacimiento <:año and a.fallecimiento >:año")
    public List<Autores> AutoresVivos(int año);

    public Optional<Autores> findByNombreContainsIgnoreCase(String nombre);
}