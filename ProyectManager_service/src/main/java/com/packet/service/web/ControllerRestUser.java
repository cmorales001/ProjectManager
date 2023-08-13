/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.service.web;

import com.packet.service.model.*;
import com.packet.service.service.ProyectService;
import com.packet.service.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v2")
public class ControllerRestUser {

    // conexion a la capa de servicio de usuarios (logica del negocio)
    @Autowired
    private UserService userService;

    //conexion a la capa de servicio de proyectos (logica del negocio)
    @Autowired
    private ProyectService proyectService;

    /**
     * peticion get para obtener los usuarios pertenecientes a un proyecto
     *
     * @param idProyect ID del proyecto
     * @return lista de usuarios
     */
    @GetMapping("/users/{idProyect}")
    public List<User> getUsersByProyect(@PathVariable("idProyect") Long idProyect) {
        List<Long> idsUsers;
        idsUsers = proyectService.findUsersByProyect(idProyect);
        List<User> usersByProyect = userService.getUserByProyect(idsUsers);
        return usersByProyect;
    }

    /**
     * peticion get para obtener un usuario por su id
     *
     * @param id ID del Usuario a buscar
     * @return Objeto DTO User
     */
    @GetMapping("/user/{id}")
    public User getUsersByID(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    /**
     * peticion get para obtener un user por su email o nickname
     *
     * @param emailOrNick nickName o Email por el que se buscará un usuario
     * @return Objeto DTO User
     */
    @GetMapping("/usermail/{emailOrNick}")
    public User getUsersByEmailOrNick(@PathVariable("emailOrNick") String emailOrNick) {
        return userService.getUserByEmailOrNick(emailOrNick);
    }

    /**
     * peticion post para guardar un nuevo user
     *
     * @param user Objeto DTO User a ser registrado
     * @return objeto de tipo ResponseEntity para comunicar al cliente el estado
     * de la peticion: Status 200 (se registro correctamente ) o Status
     * 500(ocurrio un problema en el servidor y no se registró el cambio) o
     * Status 400 (el parámetro no paso una validación y no se registró el
     * cambio)
     */
    @PostMapping("/user")
    public ResponseEntity<Void> saveUser(@RequestBody User user) {

        try {
            boolean isSaved = userService.saveUser(user);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK).
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build(); // Devuelve un código de estado 400 (Bad Request).
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// Devuelve un código de estado 500 (Server Error).
        }
    }

    /**
     * peticion post comprobar las credenciales de un usuario(inicio de sesion)
     *
     * @param user Objeto UserCredentialsDTO para recibir las credenciales del
     * usuario a través del cuerpo de la peticion (Seguridad)
     * @return ObjetoDTO User
     */
    @PostMapping("/userAccess")
    public User LogginUser(@RequestBody UserCredentialsDTO user) {
        return userService.LoginUser(user);
    }

    /**
     * metodo post para actualizar el registro de un usuario
     * @param user ObjetoDTO User
     * @return objeto de tipo ResponseEntity para comunicar al cliente el estado
     * de la peticion: Status 200 (se registro correctamente ) o Status
     * 500(ocurrio un problema en el servidor y no se registró el cambio) o
     * Status 400 (el parámetro no paso una validación y no se registró el
     * cambio)
     */
    @PutMapping("/user")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {

        try {
            boolean isSaved = userService.updateUser(user);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build();// Devuelve un código de estado 400 (Bad Request).
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// Devuelve un código de estado 500 (Server Error).
        }
        
    }

}
