/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.service.dao;

import com.packet.service.model.Proyect;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * clase Proyect Dao encargada de conectarse a la api rest ful de la bdd Mongo
 * en caso de que la dbb se caiga o la api falle, el problema no afecta a la capa de servicio(API service) y funciona sin problemas
   ventajas : bajo acoplamiento, se puede cambiar de bdd sin muchos esfuerzo(operaciones crud)
 */
@Repository
public class ProyectDaoImpl  implements ProyectDao{
    
    // url para realizar peticiones a la dbb mysql 
    private final String apiUrlBase = "http://localhost:8082/api/v1/proyect";

    @Autowired
    private RestTemplate restTemplate;

    //metodo DAO para obtener un proyecto por su id desde la bdd
    @Override
    public Proyect findById(Long id) {

        String apiUrl = apiUrlBase + "/" + id; // URL de la API RESTful 

        try {
            // Hacer una solicitud GET y obtener la respuesta en un objeto proyect
            ResponseEntity<Proyect> response = restTemplate.getForEntity(apiUrl, Proyect.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                return null;
            }

        } catch (Exception e) {
            throw e;
        }
    }

    //metodo DAO para obtener los proyectos pertenecientes a un user por su id desde la bdd
    @Override
    public List<Proyect> findProyectsByUser(Long idUser) {

        String apiUrl = apiUrlBase + "s/" + idUser; // URL de la API RESTful 

        try {
            // Hacer una solicitud GET y obtener la respuesta en una lista de proyectos
            ResponseEntity<List<Proyect>> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Proyect>>() {
            }
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                return null;
            }

        } catch (Exception e) {
            throw e;
        }
    }
    
    //metodo DAO para obtener un proyecto por su codeInvitation desde la bdd
    @Override
    public Proyect findByCodeInvitation(String code) {

        String apiUrl = apiUrlBase + "code/" + code; // URL de la API RESTful 

        try {
            // Hacer una solicitud GET y obtener la respuesta en un objeto proyect
            ResponseEntity<Proyect> response = restTemplate.getForEntity(apiUrl, Proyect.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                return null;
            }

        } catch (Exception e) {
            throw e;
        }
    }

    //metodo DAO para guardar un registro de usuario a la bdd
    @Override
    public boolean save(Proyect proyect) {
        String apiUrl = apiUrlBase;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Proyect> request = new HttpEntity<>(proyect, headers);

        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(apiUrl, request, Void.class);
            // Comprobar el código de estado de la respuesta para determinar si se eliminó correctamente
            if (response.getStatusCode().is2xxSuccessful()) {
                // La operación de eliminación fue exitosa
                return true;
            } else {
                // Manejar el código de estado inesperado aquí si es necesario
                return false;
            }
        } catch (HttpServerErrorException.InternalServerError e) {
            // En caso de error 500 en la API, lanza una excepción personalizada o registra el error
            throw new RuntimeException("Error al guardar el Proyect en la API  en la capa dao", e);
        } catch (Exception e) {
            // Captura cualquier otra excepción y regístrala o maneja adecuadamente según tus necesidades
            throw new RuntimeException("Error desconocido al guardar el Proyect", e);
        }

    }

    //metodo DAO para actualizar un proyecto en la bdd
    @Override
    public boolean update(Proyect proyect) {
        String apiUrl = apiUrlBase;

        // Configurar el objeto proyect en el cuerpo de la solicitud
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Proyect> request = new HttpEntity<>(proyect, headers);

        try {
            // Hacer la solicitud PUT y obtener la respuesta en una entidad ResponseEntity<Void>
            ResponseEntity<Void> response = restTemplate.exchange(apiUrl, HttpMethod.PUT, request, Void.class);

            // Comprobar el código de estado de la respuesta para determinar si se actualizó correctamente
            return response.getStatusCode() == HttpStatus.OK;

        } catch (Exception e) {
            // Manejo de excepciones aquí (por ejemplo, log o lanzar una excepción)
            throw e;
        }
    }

    //metodo DAO para elimincar un proyecto de la bdd
    @Override
    public boolean deleteById(Long idProyect) {
        String apiUrl = apiUrlBase + "/" + idProyect;

        try {
            // Hacer la solicitud DELETE y obtener la respuesta en una entidad ResponseEntity<Void>
            ResponseEntity<Void> response = restTemplate.exchange(apiUrl, HttpMethod.DELETE, null, Void.class);

            // Comprobar el código de estado de la respuesta para determinar si se eliminó correctamente
            if (response.getStatusCode().is2xxSuccessful()) {
                // La operación de eliminación fue exitosa
                return true;
            } else {
                // Manejar el código de estado inesperado aquí si es necesario
                return false;
            }

        } catch (HttpServerErrorException.InternalServerError e) {
            // En caso de error 500 en la API, lanza una excepción personalizada o registra el error
            throw new RuntimeException("Error al guardar el Proyect en la API", e);
        
        } catch (Exception e) {

            throw e;
        }
    }

}
