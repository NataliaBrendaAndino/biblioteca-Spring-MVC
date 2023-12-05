package com.example.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entidades.Libro;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long> {

    /*
     * Sin JPA ----
     * 
     * @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
     * public Libro buscarPorTitulo(@Param("titulo") String titulo);
     * 
     * @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombre")
     * public List<Libro> buscarPorAutor(@Param ("nombre") String nombre);
     * 
     */

    // con JPA

    public abstract Libro findByTitulo(String titulo);

    // Método de búsqueda derivado para encontrar libros por el nombre del autor
    List<Libro> findByAutorNombre(String nombreAutor);

}