/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.packet.proyects.dao;

import com.packet.proyects.model.Proyect;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * Capa Dao que contiene todos los metodos para realizar operacion Crud con Spring
 *
 */
@Repository
public interface ProyectDao extends MongoRepository<Proyect, Long> {

    /**
     * consulta personalizada que busca proyectos por el ID de usuario.
     *
     * @param idUser El ID del usuario.
     * @return Una lista de proyectos relacionados con el usuario.
     */
    @Query("{'users._id': ?0}")
    public List<Proyect> findProyectsByUser(@Param("idUser") Long idUser);
    
    /**
     * consulta personalizada que busca proyectos por su codigo de Invitacion
     * @param codeInvitation Codigo de invitacion de un proyecto
     * @return Un proyecto relacionado a un codigo de invitacion en específico
     */
    @Query("{'codeInvitation': ?0}")
    Proyect findProyectsByCodeInvitation(String codeInvitation);
    
    /**
     * consulta personalizada que busca proyectos por su idOwner(dueño de  proyecto)
     * @param idOwner El ID del usuario dueño de un proyecto.
     * @return  Una lista de proyectos relacionados con el usuario dueño.
     */
    @Query("{'idOwner': ?0}")
    List<Proyect> findProyectsByIdOwner(Long idOwner);

}
