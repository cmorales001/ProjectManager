/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.proyects.web;

import com.packet.proyects.model.Proyect;
import com.packet.proyects.service.ProyectService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author KrauserPC
 */
@RestController
@RequestMapping("/api/v1")
public class ProyectController {

    @Autowired
    private ProyectService proyectService;
    
    @GetMapping("/proyectcode/{code}")
    public ResponseEntity<Proyect> getByCode(@PathVariable("code") String codeProyect) {
        try {
            Proyect proyect = this.proyectService.findByCode(codeProyect);
            return ResponseEntity.ok(proyect);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/proyect/{id}")
    public ResponseEntity<Proyect> getById(@PathVariable("id") Long idProyect) {
        try {
            Proyect proyect = this.proyectService.findById(idProyect);
            return ResponseEntity.ok(proyect);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/proyects/{id}")
    public ResponseEntity<List<Proyect>> proyectsByUser(@PathVariable("id") Long idUser) {

        try {

            List<Proyect> proyects = this.proyectService.findProyectsByUser(idUser);
            return ResponseEntity.ok(proyects);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

    @PostMapping("/proyect")
    public ResponseEntity<Void> saveProyect(@RequestBody Proyect proyect) {

        try {
            this.proyectService.save(proyect);
            return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Devuelve un código de estado 500 (Error interno del servidor) sin contenido en el cuerpo de la respuesta.
        }
        
    }

    @PutMapping("/proyect")
    public ResponseEntity<Void> updateProyect(@RequestBody Proyect proyect) {
        try {
            this.proyectService.update(proyect);
            return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Devuelve un código de estado 500 (Error interno del servidor) sin contenido en el cuerpo de la respuesta.
        }

    }

    @DeleteMapping("/proyect/{id}")
    public ResponseEntity<Void> deleteProyect(@PathVariable("id") Long idProyect) {
        try {
            this.proyectService.deleteById(idProyect);
            return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Devuelve un código de estado 500 (Error interno del servidor) sin contenido en el cuerpo de la respuesta.
        }

    }

}
