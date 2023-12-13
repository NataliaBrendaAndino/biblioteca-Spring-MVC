package com.example.demo.entidades;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Imagen {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    // atributo que asigna el formato del archivo de la imagen
    private String mime;

    private String nombre;

    // arreglo de bytes donde se guardará
    // con Log le decimos que es pesado
    // con basic le decimos que el tipo de carga es perezosa
    // (se cargará sólo cuando se pida)
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;

}
