package com.example.demo.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entidades.Usuario;
import com.example.demo.servicios.UsuarioServicio;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/perfil/{id}")
    // en la ruta perfil/id_del_usuario veremos su imagen
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id) {
        Usuario usuario = usuarioServicio.getOne(id);

        // esto es lo que necesitamos que el navegador descargue
        byte[] imagen = usuario.getImagen().getContenido();

        // como las imágenes se leen como URL, en la cabecera le indicamos que viaja una
        // imagen
        HttpHeaders headers = new HttpHeaders();

        // configuramos la cabecera para decirle que contiene una imagen
        headers.setContentType(MediaType.IMAGE_JPEG);

        // retornamos una responsEntity de bytes :)
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
}
