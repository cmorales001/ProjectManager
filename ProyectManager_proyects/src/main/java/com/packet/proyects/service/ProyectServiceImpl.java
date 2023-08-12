/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.proyects.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.packet.proyects.dao.ProyectDao;
import com.packet.proyects.model.Proyect;
import java.util.List;

/**
 * clase de servicio que se comunica con los métodos DAO del objeto proyect
 *  
 */

@Service 
@RequiredArgsConstructor
public class ProyectServiceImpl implements ProyectService {

    //objeto DAO para hacer la comunicación con la capa service
    private final ProyectDao proyectDao;

    /**
     * Busca proyectos por el ID de usuario.
     *
     * @param idUser Id del usuario
     * @return lista de registros de proyectos de un usuario en específico
     */
    @Override
    public List<Proyect> findProyectsByUser(Long idUser) {
        List<Proyect> proyects = this.proyectDao.findProyectsByUser(idUser);
        return proyects;
    }

    /**
     * Retorna un proyecto buscado por su ID
     *
     * @param id ID del proyecto de buscar
     * @return un objeto DTO con registro de proyecto
     */
    @Override
    public Proyect findById(Long id) {
        return this.proyectDao.findById(id).orElse(null);
    }

    /**
     * Busca un proyecto por su codigo de invitación
     *
     * @param codeProyect
     * @return un objeto DTO con registro de proyecto
     */
    @Override
    public Proyect findByCode(String codeProyect) {
        return this.proyectDao.findProyectsByCodeInvitation(codeProyect);
    }
    
    /**
     * Metodo Save para guardar un registro de proyecto
     *
     * @param proyect se recibe un objeto dto proyect no se hace retorno
     */
    @Override
    public void save(Proyect proyect) {
        // asigno un id automaticamente
        proyect.set_id(this.lastId());
        this.proyectDao.insert(proyect);
    }
    
    /**
     * actualiza un registro de proyecto
     * @param proyect objeto dto de proyecto a ser actualizado
     */
    @Override
    public void update(Proyect proyect) {
        this.proyectDao.save(proyect);
    }

    /**
     * Elimina un registro de proyecto por su ID
     * @param id ID del proyecto
     */
    @Override
    public void deleteById(Long id) {
        this.proyectDao.deleteById(id);
    }
    
    /**
     * busca el id con el número mas alto de los registros en la BDD
     *
     * @return el numero del ultimo registro adicinado 1, para un nuevo registro
     */
    private Long lastId() {
        Long id = 0L;
        
        //obtengo todos los registro de la bdd
        List<Proyect> proyects = this.proyectDao.findAll();

        for (Proyect proyect : proyects) {
            if (proyect.get_id() > id) {
                id = proyect.get_id();
            }
        }
        
        return id + 1;
    }

    

}
