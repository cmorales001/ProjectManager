/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.service.dao;

import com.packet.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

// clase UserDaoImpl encargada de conectarse a la api rest ful de la bdd MySql(operaciones crud)
// en caso de que la dbb se caiga o la api falle, el problema no afecta a la capa de servicio(API service) y funciona sin problemas
// ventajas : bajo acoplamiento, se puede cambiar de bdd sin muchos esfuerzo
@Repository
public class UserDaoImpl implements UserDao {

    // url para realizar peticiones a la dbb mysql 
    private final String apiUrlBase = "http://localhost:8081/api/v1/user";

     // objeto web RESTful para interactuar con la API REST CRUD
    @Autowired
    private RestTemplate restTemplate;


    /**
     * metodo DAO para obtener un usuario por su id desde la bdd
     * @param id ID del usuario a buscar
     * @return Objeto DTO de User 
     */
    @Override
    public User findUserById(Long id) {

        //se ajusta la url base para realizar la petición
        String apiUrl = apiUrlBase + "/" + id; // URL de la API RESTful 

        try {
            // Hacer una solicitud GET y obtener la respuesta en un objeto User
            ResponseEntity<User> response = restTemplate.getForEntity(apiUrl, User.class);

            return response.getBody();

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * metodo DAO para obtener un usuario por su email o nickname desde la bdd
     * @param email email del usuario a buscar
     * @param nick nickName del usuario a buscar
     * @return  Objeto DTO de User 
     */
    @Override
    public User findUserByEmailOrNick(String email, String nick) {
        String apiUrl = apiUrlBase + "/" + email + "/" + nick; // URL de la API RESTful local

        try {
            // Hacer una solicitud GET y obtener la respuesta en un objeto User
            ResponseEntity<User> response = restTemplate.getForEntity(apiUrl, User.class);

            return response.getBody();

        } catch (Exception e) {
            // Manejo de excepciones aquí (por ejemplo, log o lanzar una excepción)
            throw e;
        }
    }


    /**
     * metodo DAO para guardar un usuario a la bdd
     * @param user Objeto DTO a ser registrado
     * @return boolean parra confirmar si se ocurrio un cambio en la bdd
     */
    @Override
    public boolean save(User user) {
        String apiUrl = apiUrlBase; // URL de la API RESTful 

        // Configurar el objeto User en el cuerpo de la solicitud
        // con el objetivo de recibir una respuesta de la bdd(se almaceno u ocurrio un problema)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        try {
            // Hacer la solicitud POST y obtener la respuesta en una entidad ResponseEntity<Void>
            ResponseEntity<Void> response = restTemplate.postForEntity(apiUrl, request, Void.class);

            // Comprobar el código de estado de la respuesta para determinar si se guardó correctamente
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            // Manejo de excepciones aquí (por ejemplo, log o lanzar una excepción)
            throw e;
        }
    }

    /**
     * metodo DAO para actualizar un registro de usuario en la bdd
     * @param user Objeto DTO a ser registrado
     * @return boolean parra confirmar si se ocurrio un cambio en la bdd
     */
    @Override
    public boolean update(User user) {
        String apiUrl = apiUrlBase;// URL de la API RESTful 

        // Configurar el objeto User en el cuerpo de la solicitud
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        try {
            // Hacer la solicitud PUT y obtener la respuesta en una entidad ResponseEntity<Void>
            ResponseEntity<Void> response = restTemplate.exchange(apiUrl, HttpMethod.PUT, request, Void.class);

            // Comprobar el código de estado de la respuesta para determinar si se actualizó correctamente
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            throw e;
        }
    }

}
