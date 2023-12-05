package com.example.demo.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entidades.Autor;
import com.example.demo.excepciones.MyException;
import com.example.demo.servicios.AutorServicio;

@Controller
@RequestMapping("/autor") // localhost:8080/autor
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    // este es para ver el formulario
    @GetMapping("/registrar")
    public String registrar() {
        return "autor_form.html";
    }

    // este es para que viaje el "nombre" que el usuario haya cargado en el
    // formulario
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {
        try {
            autorServicio.crearAutor(nombre);
            modelo.put("exito", "El Autor fue registrado correctamente!");
            System.out.println(nombre);
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_form.html";
        }
        return "redirect:../admin/dashboard";
    }

    // otros controladores
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);
        return "autor_list.html";
    }

    // midifiqué acá respecto V2, ya no se pide nombre en el paràmetro
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) throws MyException {
        modelo.put("autor", autorServicio.getOne(id));
        return "autor_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificarPost(@PathVariable String id, String nombre, ModelMap modelo) {
        try {
            // este método del servicio "modificarAutor"
            // puede lanzar una excepción, la envolvemos en un try-catch
            autorServicio.modificarAutor(nombre, id);
            return "redirect:../lista";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }
    }

    // @GetMapping("{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) throws MyException {
        autorServicio.eliminar(id);
        return "autor_modificar.html";
    }

}