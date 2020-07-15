package com.prueba.jes.demo.domains;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ministerio_publico")
public class MinisterioPublico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String ubicacion;

    private String telefono;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    private static final long serialVersionUID = 1L;
}
