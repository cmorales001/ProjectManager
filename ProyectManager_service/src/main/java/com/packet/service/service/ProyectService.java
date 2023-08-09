/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.packet.service.service;

import com.packet.service.model.Proyect;
import com.packet.service.model.Task;
import java.util.List;

/**
 *
 * @author KrauserPC
 */
public interface ProyectService {

    public boolean saveProyect(Proyect proyect);

    public boolean updateProyect(Proyect proyect);

    public Proyect getProyectById(Long idProyect);
    
    public Proyect getProyectByCodeInvitation(String code);

    public boolean deleteProyect(Long idProyect);

    public List<Proyect> getProyectsByUser(Long idUser);

    public boolean addUser(Long idProyect, Long idUser);

    public List<Long> findUsersByProyect(Long idProyect);

    public boolean saveTask(Long idProyect, Task task);

    public boolean deleteTask(Long idProyect, Task task);

    public boolean updateTask(Long idProyect, Task task);
    

}
