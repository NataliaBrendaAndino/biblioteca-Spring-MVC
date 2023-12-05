package com.example.demo.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entidades.Editorial;
import com.example.demo.excepciones.MyException;
import com.example.demo.repositorio.EditorialRepositorio;

@Service
public class EditorialServicio {
    @Autowired
    EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws MyException {

        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales() {

        List<Editorial> editoriales = new ArrayList();
        editoriales = editorialRepositorio.findAll();
        return editoriales;
    }

    @Transactional(readOnly = true)
    public Editorial getOne(String id) {
        return editorialRepositorio.findById(id).get();
    }

    @Transactional
    public void modificarEditorial(String id, String nombre) throws MyException {

        validar(nombre);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }
    }

    @Transactional
    public void eliminar(String id) throws MyException {

        Editorial editorial = editorialRepositorio.findById(id).get();
        editorialRepositorio.delete(editorial);
    }

    private void validar(String nombre) throws MyException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MyException("el nombre de la editorial no puede ser nulo o estar vacio");
        }
    }
}
