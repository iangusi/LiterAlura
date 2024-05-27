package com.aluraChallenge.LiterAlura.repository;

import com.aluraChallenge.LiterAlura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibrosRepository extends JpaRepository<Libros,Long> {
    public List<Libros> findByIdioma(String idioma);
    public List<Libros> findTop10ByOrderByDescargasDesc();
}
