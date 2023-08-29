/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.packet.proyects.service;

import com.packet.proyects.model.Proyect;
import java.util.List;

/**
 *
 * @author KrauserPC
 */
public interface ProyectService {

    public void save(Proyect proyect);

    public void update(Proyect proyect);

    public List<Proyect> findProyectsByUser(Long idUser);

    public Proyect findById(Long id);

    public void deleteById(Long id);

    public Proyect findByCode(String codeProyect);

    public List<Proyect> findProyectsByOwner(Long idOwner);

}
