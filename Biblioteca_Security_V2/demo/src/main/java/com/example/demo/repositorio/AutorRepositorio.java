package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entidades.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {

}