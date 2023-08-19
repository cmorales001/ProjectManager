/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.packet.service.dao;

import com.packet.service.model.Proyect;
import java.util.List;

/**
 *
 * @author KrauserPC
 */
public interface ProyectDao {
    //metodo DAO para obtener un proyecto por su id desde la bdd

    public Proyect findById(Long id);

    //metodo DAO para obtener los proyectos pertenecientes a un user por su id desde la bdd
    public List<Proyect> findProyectsByUser(Long idUser);

    //metodo DAO para guardar un registro de usuario a la bdd
    public boolean save(Proyect proyect);

    //metodo DAO para actualizar un proyecto en la bdd
    public boolean update(Proyect proyect);

    //metodo DAO para elimincar un proyecto de la bdd
    public boolean deleteById(Long idProyect);
    
    //metodo DAO para obtener un proyecto por su Codigo
    public Proyect findByCodeInvitation(String code);
    
    public List<Proyect> findProyectsByOwner(Long idOwner);
}
