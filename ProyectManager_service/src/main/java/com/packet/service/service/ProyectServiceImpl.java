/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.service.service;

import com.packet.service.dao.DaoImpl;
import com.packet.service.dao.ProyectDao;
import com.packet.service.model.Proyect;
import com.packet.service.model.ProyectUser;
import com.packet.service.model.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase de serivicio para los objetos DTO Proyect
 */
@Service
public class ProyectServiceImpl implements ProyectService {

    //conexión con la Capa Dao
    private final ProyectDao proyectDao;

    @Autowired
    public ProyectServiceImpl(DaoImpl factory) {
        //metodo contructor que inicializa un objeto ProyectDao con su factory
        this.proyectDao = factory.createProyectDao();
    }

    /**
     * Guarda un proyecto en la BDD luego de revisar las validaciones necesarias
     *
     * @param proyect Objeto DTO proyect a ser resgistrado
     * @return boolean (True)si se guardo correctamente, (False) si no se guardo
     * p
     */
    @Override
    public boolean saveProyect(Proyect proyect) {

        try {
            // primero se comprueba que no exista un proyecto con el mismo nombre, del mismo user
            if (this.existsName(proyect)) {
                // si existe instantaneamente no se registra e informa a la otra capa que no se ejecuto
                return false;

            }

            // creo un user para guardar en el proyecto(dbb mongo)
            ProyectUser proyectUser = new ProyectUser();

            // se le asigna su rol de admin
            proyectUser.set_id(proyect.getIdOwner());
            proyectUser.setRole("ADMIN");

            // se crea la lista de usuarios(de inicio vacia)
            List<ProyectUser> users = new ArrayList<>();
            // y se agrega al usuario a lista de users
            users.add(proyectUser);
            proyect.setUsers(users);

            // se le asigna un codigo de invitacion 
            proyect.setCodeInvitation(this.generateCodeInvitation());
            // y finalmente se envia a la capa Dao para almacenar
            this.proyectDao.save(proyect);
            // si la operacion fue exitosa retorna un true
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * actualiza un proyecto en la BDD luego de revisar las validaciones
     * necesarias
     *
     * @param proyect Objeto DTO proyect a ser actualizado
     * @return boolean (True)si se actualizó correctamente, (False) si no se
     * actualizó
     */
    @Override
    public boolean updateProyect(Proyect proyect) {

        // se obtiene el proyecto original de la bdd(su nombre registrado)
        String proyectNameDb = this.getProyectById(proyect.get_id()).getName();
        try {
            // se comprueba que el nombre sea diferente para revisar que no exista otro proyecto con el mismo nombre
            if (!proyectNameDb.equals(proyect.getName())) {
                if (this.existsName(proyect)) {
                    return false;
                }
            }
            // se registran los cambios y se retorna true
            this.proyectDao.update(proyect);
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    /**
     * Obtiene un proyecto por su ID desde la capa DAO
     *
     * @param idProyect ID del proyecto a buscar
     * @return Objeto DTO proyect o null en caso de error o no existir
     */
    @Override
    public Proyect getProyectById(Long idProyect) {
        try {
            return this.proyectDao.findById(idProyect);

        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Obtiene un proyecto por su codigo de invitación
     *
     * @param code codigo de invitación de un proyecto
     * @return Objeto DTO Proyect o null en caso de no existir
     */
    @Override
    public Proyect getProyectByCodeInvitation(String code) {
        try {
            return this.proyectDao.findByCodeInvitation(code);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Elimina un proyecto por su ID
     *
     * @param idProyect ID del proyecto a eliminar
     * @return boolean (True)si se eliminó correctamente, (False) si no se
     * realizó
     */
    @Override
    public boolean deleteProyect(Long idProyect) {
        try {
            this.proyectDao.deleteById(idProyect);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene los proyectos a los que pertenece un usuario, buscado por su ID
     *
     * @param idUser ID del usuario participante
     * @return Lista de objetos DTO Proyects (proyectos) en el que participa el
     * usuario
     */
    @Override
    public List<Proyect> getProyectsByUser(Long idUser) {

        try {
            return this.proyectDao.findProyectsByUser(idUser);

        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Agrega un usuario a un proyecto por su ID (Implementado para ser agregado
     * por otro usuario)
     *
     * @param idProyect ID del proyecto a ser agregado
     * @param idUser ID del usuario por agregar
     * @return boolean (True) si fue agregado, (False) si no se agregó
     */
    @Override
    public boolean addUser(Long idProyect, Long idUser) {

        try {
            Proyect proyect = this.getProyectById(idProyect);

            return this.addUserDb(proyect, idUser);

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Agrega un usuario a un proyecto por el codigo de invitación del mismo
     * (Implementado para ser agregado a un proyecto por el mismo usuario)
     *
     * @param codeProyect codigo del proyecto a ser agregado
     * @param idUser ID del usuario por agregar
     * @return boolean (True) si fue agregado, (False) si no se agregó
     */
    @Override
    public boolean addUser(String codeProyect, Long idUser) {

        try {
            Proyect proyect = this.getProyectByCodeInvitation(codeProyect);
            return this.addUserDb(proyect, idUser);

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Guarda una tarea dentro de un proyecto y posteriormente en la BDD
     *
     * @param idProyect ID del proyecto al que pertenece
     * @param task Objeto DTO Task que contiene la información
     * @return boolean (True) si fue agregado, (False) si no se agregó
     */
    @Override
    public boolean saveTask(Long idProyect, Task task) {
        // obtiene el proyecto al que pertenece
        Proyect proyect = this.getProyectById(idProyect);
        // obtiene sus tareas
        List<Task> tasks = proyect.getTasks();
        // si no tiene se inicializa una nueva lista
        if (tasks==null) {
            tasks = new ArrayList<>();
        }
        // se le asigna un ID con ayuda del método lastID()
        task.set_id(this.lastId(tasks));
        // se agrega a la lista de tareas
        tasks.add(task);
        // es almacenado en la BDD y se retorna true
        proyect.setTasks(tasks);
        this.updateProyect(proyect);
        return true;
    }

    /**
     * Elimina una tarea dentro de un proyecto ylo registra en la BDD
     *
     * @param idProyect ID del proyecto al que pertenece
     * @param task Objeto DTO Task a eliminar
     * @return boolean (True) si fue eliminado, (False) si no se eliminó
     */
    @Override
    public boolean deleteTask(Long idProyect, Task task) {
        // obtiene el proyecto al que pertenece
        Proyect proyect = this.getProyectById(idProyect);
        // obtiene sus tareas
        List<Task> tasks = proyect.getTasks();
        // si no hay tareas retorna false
        if (tasks==null) {
            return false;
        }
        // elimina la tarea deseada
        tasks.remove(task);
        //y se almacena el cambio
        proyect.setTasks(tasks);
        this.updateProyect(proyect);
        return true;
    }

    /**
     * Actualiza una tarea dentro de un proyecto ylo registra en la BDD
     *
     * @param idProyect ID del proyecto al que pertenece
     * @param task Objeto DTO Task a actualizar
     * @return boolean (True) si fue actualizado, (False) si no se actualizó
     */
    @Override
    public boolean updateTask(Long idProyect, Task task) {
        // obtiene el proyecto al que pertenece
        Proyect proyect = this.getProyectById(idProyect);
        // obtiene sus tareas
        List<Task> tasks = proyect.getTasks();
        // si no hay tareas retorna false
        if (tasks==null) {
            return false;
        }
        int index = 0;

        // obtiene el indice de la tarea actualizar dentro de las lista de tareas
        for (Task taskBd : tasks) {
            if (taskBd.get_id().equals(task.get_id())) {
                index = tasks.indexOf(taskBd);
                break;
            }
        }
        // registar el cambio y almacena en la BDD
        tasks.set(index, task);
        proyect.setTasks(tasks);
        this.updateProyect(proyect);
        return true;
    }

    /**
     * Encuentra los IDs de los usuarios que pertenecen a un proyecto
     *
     * @param idProyect ID del proyecto a examinar
     * @return lista de Long con los IDs de los usuarios participantes
     */
    @Override
    public List<Long> findUsersByProyect(Long idProyect) {

        // obtiene el registro de proyecto
        Proyect proyect = this.getProyectById(idProyect);
        // si el proyecto es nulo o no tiene usuarios retorna null
        if(proyect==null || proyect.getUsers()==null){
            return null;
        }
        // se crea la lista de retorno
        List<Long> idsUsers = new ArrayList<>();
        
        // se añade los IDs de los participantes
        for (ProyectUser user : proyect.getUsers()) {
            idsUsers.add(user.get_id());
        }
        return idsUsers;

    }

    /**
     * agrega un usuario a un proyecto 
     * (usado por las 2 maneras de añadir un usuario)
     * @param proyect
     * @param idUser
     * @return 
     */
    private boolean addUserDb(Proyect proyect, Long idUser) {
        //comprueba que el usuario a ser añadido no exista
        List<ProyectUser> users = proyect.getUsers();
        for (ProyectUser user : users) {
            if (user.get_id().equals(idUser)) {
                // si ya está retorna false
                return false;
            }
        }
        // se crea le registro con el Rol USER y se almacena en la BDD
        ProyectUser proyectUser = new ProyectUser();
        proyectUser.set_id(idUser);
        proyectUser.setRole("USER");
        users.add(proyectUser);
        proyect.setUsers(users);
        this.proyectDao.update(proyect);
        return true;
    }

    /**
     * Comprueba la existencia de un proyecto por su nombre y el usuario que crea el proyecto
     * @param proyect Objeto DTO Proyect a ser registrado
     * @return (True) si un proyecto con el mismo nombre existe para un usuario(y es el dueño), 
     * (False) si existe un proyecto con el mismo nombre o no(pero no es dueño)
     */
    private boolean existsName(Proyect proyect) {

        try {
            // id del creador del proyecto
            Long idOwner = proyect.getIdOwner();
            //proyectos al que pertenece el dueño del proyecto nuevo
            List<Proyect> proyects = this.getProyectsByUser(idOwner);
            // nombre del proyecto a crear
            String proyectName = proyect.getName();
            // se hace la busqueda de los proyectos para comprobar si existe alguno en el que es dueño y se repite el mismo nombre
            for (Proyect proyectsName : proyects) {
                if (proyectsName.getIdOwner().equals(idOwner) && proyectsName.getName().equals(proyectName)) {
                    //si hay coincidencia se retorna true; 
                    return true;
                }
            }
            // si no se encuentra coincidencia se retorna false;
            return false;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * Genera un código aleatorio para identificar el proyecto
     * @return codigo para proyecto
     */
    private String generateCodeInvitation() {
        Random rdn = new Random();

        String caracteres = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        char letra;
        String code = "";

        // se crea un codigo con caracteres aleatorios en el bucle for
        for (int i = 0; i < 10; i++) {
            letra = caracteres.charAt(rdn.nextInt(caracteres.length() - 1));
            code += Character.toString(letra);
        }

        // se retorna el código de invitación generado
        return code;
    }

    /**
     * Busca el ultimo ID de una lista de tareas y lo retorna adicionado 1 para registrar una nueva tare
     * @param tasks Objeto DTO Task
     * @return nuevo ID para tarea
     */
    private Long lastId(List<Task> tasks) {
        Long id = 0L;
        for (Task task : tasks) {
            if (task.get_id() > id) {
                id = task.get_id();
            }
        }
        return id + 1;
    }

}
