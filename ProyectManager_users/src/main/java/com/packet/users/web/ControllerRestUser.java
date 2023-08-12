/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.users.web;

import com.packet.users.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.packet.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1")
public class ControllerRestUser {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable("id") Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// Devuelve un código de estado 500 (Error interno del servidor) sin contenido en el cuerpo de la respuesta.
        }
    }

    /**
     * metodo get para obtenerun usuario por su email o nickname
     * @param email email del usuario
     * @param nick nick del usuario
     * @return objeto de tipo ResponseEntity para comunicar a cliente el estado de la peticion:
     *         Status 200 (bien ) acompañado de un registro de usuario , o  
     *         Status 500(ocurrio un problema en el servidor)
     */
    @GetMapping("/user/{email}/{nick}")
    public ResponseEntity<User> getUsersByEmailOrNick(@PathVariable("email") String email, @PathVariable("email") String nick) {
        
        try {
            User user;
            user = userService.getUserByEmailOrNickName(email, nick);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();// Devuelve un código de estado 500 (Error interno del servidor) sin contenido en el cuerpo de la respuesta.
        }
    }

    /**
     * metodo post que almacena un registro de proyecto
     * @param user User a ser guardado en la BDD
     * @returnun objeto de tipo ResponseEntity para comunicar a cliente el estado de la peticion:
     *         Status 200 (se registro correctamente ) o Status 500(ocurrio un problema en el servidor y no se registró el cambio)
     */
    @PostMapping("/user")
    public ResponseEntity<Void> saveUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return ResponseEntity.ok().build(); // Devuelve un código de estado 200 (OK) sin contenido en el cuerpo de la respuesta.
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Devuelve un código de estado 500 (Error interno del servidor) sin contenido en el cuerpo de la respuesta.
        }
    }

    
    /**
     * metodo put que actualizar un registro de proyecto
     * @param user User a ser actualizado en la BDD
     * @returnun objeto de tipo ResponseEntity para comunicar al cliente el estado de la peticion:
     *         Status 200 (se registro correctamente ) o Status 500(ocurrio un problema en el servidor y no se registró el cambio)
     */
    @PutMapping("/user")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
