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

    // peticion get para obtener los usuarios de un proyecto
    @GetMapping("/users/{idProyect}")
    public List<User> getUsersByProyect(@PathVariable("idProyect") Long idProyect) {
        List<Long> idsUsers;
        idsUsers = proyectService.findUsersByProyect(idProyect);
        List<User> usersByProyect = userService.getUserByProyect(idsUsers);
        return usersByProyect;
    }

    //peticion get para obtener un user por su id
    @GetMapping("/user/{id}")
    public User getUsersByID(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    //peticion get para obtener un user por su email o nickname
    @GetMapping("/usermail/{emailOrNick}")
    public User getUsersByEmailOrNick(@PathVariable("emailOrNick") String emailOrNick) {
        return userService.getUserByEmailOrNick(emailOrNick);
    }

    // peticion post para guardar un nuevo user
    @PostMapping("/user")
    public ResponseEntity<Void> saveUser(@RequestBody User cliente) {

        try {
            boolean isSaved = userService.saveUser(cliente);
            if (isSaved) {
                return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
            } else {
                return ResponseEntity.status((HttpStatus.BAD_REQUEST)).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //peticion post comprobar las credenciales de un usuario(inicio de sesion)
    // utiliza el objeto UserCredentialsDTO
    @PostMapping("/userAccess")
    public User LogginUser(@RequestBody UserCredentialsDTO user) {

        return userService.LoginUser(user);

    }

    //metodo post para actualizar el registro de un usuario
    @PutMapping("/user")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {

        try {
            boolean isSaved = userService.updateUser(user);
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
