/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.service.web;

import com.packet.service.model.Proyect;
import com.packet.service.model.Task;
import com.packet.service.service.ProyectService;
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
@RequestMapping("/api/v2")
public class ControllerRestProyect {

    @Autowired
    private ProyectService proyectService;

    //peticion para obtener los proyectos de un user en especial
    @GetMapping("/proyects/{id}")
    public List<Proyect> proyectsByUser(@PathVariable("id") Long idUser) {
        return this.proyectService.getProyectsByUser(idUser);
    }

    //peticion get para obtener un proyecto por su id
    @GetMapping("/proyect/{id}")
    public Proyect proyectsById(@PathVariable("id") Long idProyect) {
        return this.proyectService.getProyectById(idProyect);
    }

    @GetMapping("/proyectcode/{code}")
    public ResponseEntity<Proyect> getByCode(@PathVariable("code") String codeProyect) {

        Proyect proyect = this.proyectService.getProyectByCodeInvitation(codeProyect);
        return ResponseEntity.ok(proyect);

    }

    //peticion post para guardar un proyecto 
    @PostMapping("/proyect")
    public ResponseEntity<Void> saveProyect(@RequestBody Proyect proyect) {

        try {
            boolean isSaved = this.proyectService.saveProyect(proyect);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //peticion put para actualizar un proyecto
    @PutMapping("/proyect")
    public ResponseEntity<Void> updateProyect(@RequestBody Proyect proyect) {

        try {
            boolean isSaved = this.proyectService.updateProyect(proyect);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    // peticion delete para eliminar un proyecto
    @DeleteMapping("/proyect/{id}")
    public ResponseEntity<Void> deleteProyect(@PathVariable("id") Long idProyect) {

        try {
            boolean isSaved = this.proyectService.deleteProyect(idProyect);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // peticion put para añadir un nuevo usuario a un proyecto existente
    @PutMapping("/proyect/{idProyect}/{idUser}")
    public ResponseEntity<Void> addUserToProyect(@PathVariable("idProyect") Long idProyect, @PathVariable("idUser") Long idUser) {

        try {
            boolean isSaved = this.proyectService.addUser(idProyect, idUser);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //PETICIONES PARA TAREAS
    //peticion post para guardar una tarea en un proyecto 
    @PostMapping("/task/{idProyect}")
    public ResponseEntity<Void> saveTask(@RequestBody Task task, @PathVariable("idProyect") Long idProyect) {

        try {
            boolean isSaved = this.proyectService.saveTask(idProyect, task);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //peticion put para actualizar un proyecto
    @PutMapping("/task/{idProyect}")
    public ResponseEntity<Void> updateTask(@RequestBody Task task, @PathVariable("idProyect") Long idProyect) {

        try {
            boolean isSaved = this.proyectService.updateTask(idProyect, task);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    // peticion delete para eliminar un proyecto
    @DeleteMapping("/task/{idProyect}")
    public ResponseEntity<Void> deleteTask(@RequestBody Task task, @PathVariable("idProyect") Long idProyect) {

        try {
            boolean isSaved = this.proyectService.deleteTask(idProyect, task);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
