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
 *
 * 
 */
@Service
public class ProyectServiceImpl implements ProyectService {

    private final ProyectDao proyectDao;

    @Autowired
    public ProyectServiceImpl(DaoImpl factory) {
        this.proyectDao = factory.createProyectDao();
    }

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

            // se obtiene la lista de usuarios(de inicio vacia)
            List<ProyectUser> users = new ArrayList<>();
            users.add(proyectUser);
            // y se agrega al usuario a lista de users
            proyect.setUsers(users);

            // se le asigna un codigo de invitacion 
            proyect.setCodeInvitation(this.generateCodeInvitation());
            // y finalmente se almacena envia almacenar
            this.proyectDao.save(proyect);
            // si la operacion fue exitosa retorna un true
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateProyect(Proyect proyect) {

        String proyectNameDb = this.getProyectById(proyect.get_id()).getName();
        try {
            if (!proyectNameDb.equals(proyect.getName())) {
                if (this.existsName(proyect)) {
                    return false;
                }
            }
            this.proyectDao.update(proyect);
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Proyect getProyectById(Long idProyect) {
        try {
            return this.proyectDao.findById(idProyect);

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Proyect getProyectByCodeInvitation(String code) {
        try {
            return this.proyectDao.findByCodeInvitation(code);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public boolean deleteProyect(Long idProyect) {
        try {
            this.proyectDao.deleteById(idProyect);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Proyect> getProyectsByUser(Long idUser) {

        try {
            return this.proyectDao.findProyectsByUser(idUser);

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public boolean addUser(Long idProyect, Long idUser) {

        try {
            Proyect proyect = this.getProyectById(idProyect);

            return this.addUserDb(proyect, idUser);

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean addUser(String codeProyect, Long idUser) {

        try {
            Proyect proyect = this.getProyectByCodeInvitation(codeProyect);
            return this.addUserDb(proyect, idUser);

        } catch (Exception e) {
            return false;
        } 
    }

    public boolean addUserDb(Proyect proyect, Long idUser) {
        List<ProyectUser> users = proyect.getUsers();
        for (ProyectUser user : users) {
            if (user.get_id().equals(idUser)) {
                return false;
            }
        }
        ProyectUser proyectUser = new ProyectUser();
        proyectUser.set_id(idUser);
        proyectUser.setRole("USER");
        users.add(proyectUser);
        proyect.setUsers(users);
        this.proyectDao.update(proyect);
        return true;
    }

    @Override
    public boolean saveTask(Long idProyect, Task task) {
        Proyect proyect = this.getProyectById(idProyect);
        List<Task> tasks = proyect.getTasks();
        if (tasks.isEmpty()) {
            tasks = new ArrayList<>();
        }
        task.set_id(this.lastId(tasks));
        tasks.add(task);
        proyect.setTasks(tasks);
        this.updateProyect(proyect);
        return true;
    }

    @Override
    public boolean deleteTask(Long idProyect, Task task) {
        Proyect proyect = this.getProyectById(idProyect);
        List<Task> tasks = proyect.getTasks();
        if (tasks.isEmpty()) {
            tasks = new ArrayList<>();
        }
        tasks.remove(task);
        proyect.setTasks(tasks);
        this.updateProyect(proyect);
        return true;
    }

    @Override
    public boolean updateTask(Long idProyect, Task task) {
        Proyect proyect = this.getProyectById(idProyect);
        List<Task> tasks = proyect.getTasks();
        if (tasks.isEmpty()) {
            tasks = new ArrayList<>();
        }
        int index = 10;

        for (Task taskBd : tasks) {
            if (taskBd.get_id().equals(task.get_id())) {
                index = tasks.indexOf(taskBd);
                break;
            }
        }
        tasks.set(index, task);
        proyect.setTasks(tasks);
        this.updateProyect(proyect);
        return true;
    }

    @Override
    public List<Long> findUsersByProyect(Long idProyect) {

        try {
            Proyect proyect = this.getProyectById(idProyect);
            List<Long> idsUsers = new ArrayList<>();
            for (ProyectUser user : proyect.getUsers()) {
                idsUsers.add(user.get_id());
            }
            return idsUsers;

        } catch (Exception e) {
            return null;
        }

    }

    private boolean existsName(Proyect proyect) {

        try {
            Long idOwner = proyect.getIdOwner();

            List<Proyect> proyects = this.getProyectsByUser(idOwner);
            String proyectName = proyect.getName();
            for (Proyect proyectsName : proyects) {
                if (proyectsName.getIdOwner().equals(idOwner) && proyectsName.getName().equals(proyectName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }

    }

    private String generateCodeInvitation() {
        Random rdn = new Random();

        String caracteres = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        char letra;
        String code = "";

        // se crea un codigo con caracteres aleatorios en el bucle for
        for (int i = 0; i < 8; i++) {
            letra = caracteres.charAt(rdn.nextInt(caracteres.length() - 1));
            code += Character.toString(letra);
        }

        // se retorna el código de invitación generado
        return code;
    }

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
