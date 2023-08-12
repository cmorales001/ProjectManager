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
 * Clase que expone los end points para el uso de la API REST
 * se conecta con la capa service para relizar métodos CRUD
 */

@RestController
@RequestMapping("/api/v1")  //ruta a nivel de clase
public class ProyectController {

    // Objeto de tipo Service, para la comunicacion entre la capa web y service
    @Autowired
    private ProyectService proyectService;
    
    /**
     * metodo get que expone un proyecto buscado por su codigo de invitación
     * @param codeProyect recibe un codigo (String) para ser buscado por las capas inferiores
     * @return un objeto de tipo ResponseEntity para comunicar a cliente el estado de la peticion:
     *         Status 200 (bien ) y registro de proyecto,  Status 500(ocurrio un problema en el servidor)
     */
    @GetMapping("/proyectcode/{code}")
    public ResponseEntity<Proyect> getByCode(@PathVariable("code") String codeProyect) {
        try {
            Proyect proyect = this.proyectService.findByCode(codeProyect);
            return ResponseEntity.ok(proyect);// Devuelve un código de estado 200 (OK) con contenido un proyecto.
        } catch (Exception e) {
            // en caso de error retorna un estado 500 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
    }
    
    /**
     * metodo get para obtener un proyecto por su ID
     * @param idProyect ID de un proyecto
     * @return un objeto de tipo ResponseEntity para comunicar a cliente el estado de la peticion:
     *         Status 200 (bien ) y registro de proyecto,  Status 500(ocurrio un problema en el servidor)
     */
    @GetMapping("/proyect/{id}")
    public ResponseEntity<Proyect> getById(@PathVariable("id") Long idProyect) {
        try {
            Proyect proyect = this.proyectService.findById(idProyect);
            return ResponseEntity.ok(proyect);// Devuelve un código de estado 200 (OK) con contenido una lista de proyectos.

        } catch (Exception e) {
            // en caso de error retorna un estado 500 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    
    /**
     * metodo get para obtener los proyectos de un usuario en específico
     * @param idUser ID del usuario
     * @return un objeto de tipo ResponseEntity para comunicar a cliente el estado de la peticion:
     *         Status 200 (bien ) acompañado de una lista de proyectos en el usuario esta presente ,  
     *         Status 500(ocurrio un problema en el servidor)
     */
    @GetMapping("/proyects/{id}")
    public ResponseEntity<List<Proyect>> proyectsByUser(@PathVariable("id") Long idUser) {

        try {
            List<Proyect> proyects = this.proyectService.findProyectsByUser(idUser);
            return ResponseEntity.ok(proyects);// Devuelve un código de estado 200 (OK) con contenido una lista de proyectos.
        } catch (Exception e) {
            // en caso de error retorna un estado 500 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }
    
    /**
     * metodo post que almacena un registro de proyecto
     * @param proyect proyecto a ser guardado en la BDD
     * @returnun objeto de tipo ResponseEntity para comunicar a cliente el estado de la peticion:
     *         Status 200 (se registro correctamente ) o Status 500(ocurrio un problema en el servidor y no se registró el cambio)
     
     */
    @PostMapping("/proyect")
    public ResponseEntity<Void> saveProyect(@RequestBody Proyect proyect) {

        try {
            this.proyectService.save(proyect);
            return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
        } catch (Exception e) {
            // en caso de error retorna un estado 500 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Devuelve un código de estado 500 (Error interno del servidor) sin contenido en el cuerpo de la respuesta.
        }
        
    }
    
    /**
     * metodo put que actualizar un registro de proyecto
     * @param proyect proyecto a ser guardado en la BDD
     * @returnun objeto de tipo ResponseEntity para comunicar al cliente el estado de la peticion:
     *         Status 200 (se registro correctamente ) o Status 500(ocurrio un problema en el servidor y no se registró el cambio)
     */
    @PutMapping("/proyect")
    public ResponseEntity<Void> updateProyect(@RequestBody Proyect proyect) {
        try {
            this.proyectService.update(proyect);
            return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Devuelve un código de estado 500 (Error interno del servidor) sin contenido en el cuerpo de la respuesta.
        }

    }
    
    /**
     * metodo delete que eliminar un registro de proyecto
     * @param idProyect proyecto a ser guardado en la BDD
     * @returnun objeto de tipo ResponseEntity para comunicar al cliente el estado de la peticion:
     *         Status 200 (se registro correctamente ) o Status 500(ocurrio un problema en el servidor y no se registró el cambio)
     */
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
