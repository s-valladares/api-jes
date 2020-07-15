package com.prueba.jes.demo.controllers;

import com.prueba.jes.demo.config.UrlBaseApi;
import com.prueba.jes.demo.domains.MinisterioPublico;
import com.prueba.jes.demo.services.IMinisterioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(UrlBaseApi.URL_API)
public class MinisterioController {

    private final String entidad = "/ministerio";

    @Autowired
    private IMinisterioService mpService;
    Map<String, Object> response = new HashMap<>();

    @GetMapping(entidad)
    public ResponseEntity<?> index() {
        response.clear();
        List<MinisterioPublico> objNew = null;

        try {
            objNew = mpService.getAll();
        } catch (DataAccessException ex) {
            response.put("mensaje", "Error al obtener de la base de datos");
            response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("size", objNew.size());
        response.put("rows", objNew);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping(entidad)
    public ResponseEntity<?> create(@Valid @RequestBody MinisterioPublico x, BindingResult result) {
        response.clear();
        MinisterioPublico objNew;

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(err -> {
                return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            objNew = mpService.create(x);

        } catch (DataAccessException ex) {
            response.put("mensaje", "Error al insertar en la base de datos");
            response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "OK");
        response.put("RES", objNew);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(entidad + "/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody MinisterioPublico mp,
                                    BindingResult result, @PathVariable Long id) {

        response.clear();

        MinisterioPublico mpActual = mpService.getId(id);
        MinisterioPublico mpNuevo;

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(err -> {
                return "El campo '"+err.getField()+"'"+err.getDefaultMessage();
            }).collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (mpActual == null) {
            response.put("mensaje", "Error: no hay "+entidad+" con id: "
                    .concat(id.toString().concat(". No existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            mpActual.setUbicacion(mp.getUbicacion());
            mpActual.setTelefono(mp.getTelefono());
            mpNuevo = mpService.create(mpActual);
        } catch (DataAccessException ex) {
            response.put("mensaje", "Error actualizar el "+entidad+" en la base de datos");
            response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Actualizado correctamente");
        response.put("RES".toUpperCase(), mpNuevo);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(entidad + "/{id}" )
    public ResponseEntity<?> getMpId(@PathVariable Long id) {
        response.clear();
        MinisterioPublico obj;

        try {
            obj = mpService.getId(id);
        } catch (DataAccessException ex) {
            response.put("mensaje", "Error al consultar base de datos");
            response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (obj == null) {
            response.put("error", true);
            response.put("mensaje", "NO hay RESULTADO para el ID: " + id);
            response.put("RES", null);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @DeleteMapping(entidad + "/{id}" )
    public ResponseEntity<?> deleteMp(@PathVariable Long id) {
        response.clear();
        MinisterioPublico obj;

        try {
            obj = mpService.getId(id);
        } catch (DataAccessException ex) {
            response.put("mensaje", "Error al consultar base de datos");
            response.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (obj == null) {
            response.put("error", true);
            response.put("mensaje", "NO hay RESULTADO para el ID: " + id);
            response.put("RES", null);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            mpService.delete(id);
        }

        response.put("mensaje", "Eliminado");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
