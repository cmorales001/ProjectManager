/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.proyects.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.packet.proyects.dao.ProyectDao;
import com.packet.proyects.model.Proyect;
import com.packet.proyects.model.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author KrauserPC
 */
@Service
@RequiredArgsConstructor
public class ProyectServiceImpl implements ProyectService {

    private final ProyectDao proyectDao;

    @Override
    public void save(Proyect proyect) {
        proyect.set_id(this.lastId());
        this.proyectDao.save(proyect);

    }

    @Override
    public List<Proyect> findProyectsByUser(Long idUser) {
        List<Proyect> proyects = this.proyectDao.findProyectsByUser(idUser);
        return proyects;
    }

    @Override
    public Proyect findById(Long id) {

        return this.proyectDao.findById(id).orElse(null);

    }
    
    @Override
    public Proyect findByCode(String codeProyect) {
            return this.proyectDao.findProyectsByCodeInvitation(codeProyect);
    }

    @Override
    public void deleteById(Long id) {

        this.proyectDao.deleteById(id);

    }
    
    private Long lastId(){
        Long id=0L;
        List<Proyect> proyects=this.proyectDao.findAll();
        
        for (Proyect proyect : proyects) {

                
                if (proyect.get_id()>id) {
                    id=proyect.get_id();
                }
        }
        return id+1;
    }
    
    @Override
    public void update(Proyect proyect) {
        this.proyectDao.save(proyect);
    }

}
