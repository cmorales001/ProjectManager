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
 * Clase Dao
 * 
 */
@Repository
public interface ProyectDao extends MongoRepository<Proyect, Long> {

    @Query("{'users._id': ?0}")
    public List<Proyect> findProyectsByUser( @Param("idUser") Long idUser);
    
    @Query("{'codeInvitation': ?0}")
    Proyect findProyectsByCodeInvitation(String codeInvitation);

}
