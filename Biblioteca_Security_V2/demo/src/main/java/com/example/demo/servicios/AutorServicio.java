package com.example.demo.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entidades.Autor;
import com.example.demo.excepciones.MyException;
import com.example.demo.repositorio.AutorRepositorio;

@Service
public class AutorServicio {

    @Autowired
    AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MyException {

        validar(nombre);

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {

        List<Autor> autores = new ArrayList();
        autores = autorRepositorio.findAll();
        return autores;
    }

    @Transactional(readOnly = true)
    public Autor getOne(String id) {
        return autorRepositorio.findById(id).get();
    }

    @Transactional
    public String modificarAutor(String nombre, String id) throws MyException {

        validar(nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
            return "Usuario modificado con éxito";
        } else {
            throw new MyException("No se pudo actualizar el autor con el id " + id);
        }
    }

    @Transactional
    public void eliminar(String id) throws MyException {
        Autor autor = autorRepositorio.findById(id).get();
        autorRepositorio.delete(autor);
    }

    private void validar(String nombre) throws MyException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MyException("el nombre no puede ser nulo o estar vacio");
        }
    }

}