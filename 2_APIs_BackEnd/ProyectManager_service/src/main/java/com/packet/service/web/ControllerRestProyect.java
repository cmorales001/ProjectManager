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

@RestController
@RequestMapping("/api/v2")
public class ControllerRestProyect {

    @Autowired
    private ProyectService proyectService;

    /**
     * PETICIONES PARA OBTENER PROYECTOS
     */
    /**
     * método get para obtener los proyectos de un user en especial
     *
     * @param idUser ID del usuario a obtener sus proyectos
     * @return lista de proyectos en el que esta presente el usuario
     */
    @GetMapping("/proyects/{id}")
    public List<Proyect> proyectsByUser(@PathVariable("id") Long idUser) {
        return this.proyectService.getProyectsByUser(idUser);
    }

    /**
     * método get para obtener un proyecto por su id
     *
     * @param idProyect ID del proyecto a buscar
     * @return ObjetoDTO proyect
     */
    @GetMapping("/proyect/{id}")
    public Proyect proyectsById(@PathVariable("id") Long idProyect) {
        return this.proyectService.getProyectById(idProyect);
    }

    @GetMapping("/proyectcode/{code}")
    public ResponseEntity<Proyect> getByCode(@PathVariable("code") String codeProyect) {

        Proyect proyect = this.proyectService.getProyectByCodeInvitation(codeProyect);
        return ResponseEntity.ok(proyect);

    }

    /**
     * PETICIONES PARA GUARDAR PROYECTOS
     */
    /**
     * método post para guardar un nuevo registro de proyecto
     *
     * @param proyect ObjetoDTO proyect a ser registrado
     * @return objeto de tipo ResponseEntity para comunicar al cliente el estado
     * de la peticion: Status 200 (se registro correctamente ) o Status
     * 500(ocurrio un problema en el servidor y no se registró el cambio) o
     * Status 400 (el parámetro no paso una validación y no se registró el
     * cambio)
     */
    @PostMapping("/proyect")
    public ResponseEntity<Void> saveProyect(@RequestBody Proyect proyect) {

        try {
            boolean isSaved = this.proyectService.saveProyect(proyect);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) .
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build(); // Devuelve un código de estado 400 (Bad Request).
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// Devuelve un código de estado 500 (Server Error).
        }
    }

    /**
     * PETICIONES PARA EDITAR REGISTROS DE PROYECTOS
     */
    /**
     * método put para actualizar un proyecto
     *
     * @param proyect ObjetoDTO proyect a ser actualizado
     * @return objeto de tipo ResponseEntity para comunicar al cliente el estado
     * de la peticion: Status 200 (se registro correctamente ) o Status
     * 500(ocurrio un problema en el servidor y no se registró el cambio) o
     * Status 400 (el parámetro no paso una validación y no se registró el
     * cambio)
     */
    @PutMapping("/proyect")
    public ResponseEntity<Void> updateProyect(@RequestBody Proyect proyect) {

        try {
            boolean isSaved = this.proyectService.updateProyect(proyect);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) .
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build(); // Devuelve un código de estado 400 (Bad Request).
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Devuelve un código de estado 500 (Server Error).
        }

    }

//    // peticion put para añadir un nuevo usuario a un proyecto existente
//    @PutMapping("/proyect/{idProyect}/{idUser}")
//    public ResponseEntity<Void> addUserToProyect(@PathVariable("idProyect") Long idProyect, @PathVariable("idUser") Long idUser) {
//
//        try {
//            boolean isSaved = this.proyectService.addUser(idProyect, idUser);
//            if (isSaved) {
//                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
//            } else {
//                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build();
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
    
    /**
     * peticion put para añadir un nuevo usuario a un proyecto existente por
     * medio del codigo de invitacion de un Proyecto
     *
     * @param codeProyect codigo del proyecto a unirse
     * @param idUser ID del usuario a unirse
     * @return objeto de tipo ResponseEntity para comunicar al cliente el estado
     * de la peticion: Status 200 (se registro correctamente ) o Status
     * 500(ocurrio un problema en el servidor y no se registró el cambio) o
     * Status 400 (el parámetro no paso una validación y no se registró el
     * cambio)
     */
    @PutMapping("/proyect/{codeProyect}/{idUser}")
    public ResponseEntity<Void> addUserToProyectByCode(@PathVariable("codeProyect") String codeProyect, @PathVariable("idUser") Long idUser) {

        try {
            boolean isSaved = this.proyectService.addUser(codeProyect, idUser);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK).
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build(); // Devuelve un código de estado 400 (Bad Request
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Devuelve un código de estado 500 (Server Error)
        }

    }

    /**
     * PETICIONES PARA ELIMINAR REGISTROS DE PROYECTOS
     */
    /**
     * método delete para eliminar un proyecto
     *
     * @param idProyect ID del proyecto a Eliminar
     * @return objeto de tipo ResponseEntity para comunicar al cliente el estado
     * de la peticion: Status 200 (se registro correctamente ) o Status
     * 500(ocurrio un problema en el servidor y no se registró el cambio) o
     * Status 400 (el parámetro no paso una validación y no se registró el
     * cambio)
     */
    @DeleteMapping("/proyect/{id}")
    public ResponseEntity<Void> deleteProyect(@PathVariable("id") Long idProyect) {

        try {
            boolean isSaved = this.proyectService.deleteProyect(idProyect);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build(); // Devuelve un código de estado 400 (Bad Request
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// Devuelve un código de estado 500 (Server Error)
        }
    }

    /**
     * PETICIONES PARA TAREAS
     */
    /**
     * peticion post para guardar una tarea en un proyecto
     *
     * @param task Objeto DTO Task
     * @param idProyect ID del proyecto al que pertenece
     * @return objeto de tipo ResponseEntity para comunicar al cliente el estado
     * de la peticion: Status 200 (se registro correctamente ) o Status
     * 500(ocurrio un problema en el servidor y no se registró el cambio) o
     * Status 400 (el parámetro no paso una validación y no se registró el
     * cambio)
     */
    @PostMapping("/task/{idProyect}")
    public ResponseEntity<Void> saveTask(@RequestBody Task task, @PathVariable("idProyect") Long idProyect) {

        try {
            boolean isSaved = this.proyectService.saveTask(idProyect, task);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build(); // Devuelve un código de estado 400 (Bad Request
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// Devuelve un código de estado 500 (Server Error)
        }
    }

    /**
     * peticion post para actualizar una tarea en un proyecto
     *
     * @param task Objeto DTO Task
     * @param idProyect ID del proyecto al que pertenece
     * @return objeto de tipo ResponseEntity para comunicar al cliente el estado
     * de la peticion: Status 200 (se registro correctamente ) o Status
     * 500(ocurrio un problema en el servidor y no se registró el cambio) o
     * Status 400 (el parámetro no paso una validación y no se registró el
     * cambio)
     */
    @PutMapping("/task/{idProyect}")
    public ResponseEntity<Void> updateTask(@RequestBody Task task, @PathVariable("idProyect") Long idProyect) {

        try {
            boolean isSaved = this.proyectService.updateTask(idProyect, task);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build(); // Devuelve un código de estado 400 (Bad Request
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// Devuelve un código de estado 500 (Server Error)
        }

    }

    /**
     * peticion delete para eliminar una tarea en un proyecto
     *
     * @param task Objeto DTO Task
     * @param idProyect ID del proyecto al que pertenece
     * @return objeto de tipo ResponseEntity para comunicar al cliente el estado
     * de la peticion: Status 200 (se registro correctamente ) o Status
     * 500(ocurrio un problema en el servidor y no se registró el cambio) o
     * Status 400 (el parámetro no paso una validación y no se registró el
     * cambio)
     */
    @DeleteMapping("/task/{idProyect}")
    public ResponseEntity<Void> deleteTask(@RequestBody Task task, @PathVariable("idProyect") Long idProyect) {

        try {
            boolean isSaved = this.proyectService.deleteTask(idProyect, task);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build(); // Devuelve un código de estado 400 (Bad Request
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// Devuelve un código de estado 500 (Server Error)
        }
    }

}
