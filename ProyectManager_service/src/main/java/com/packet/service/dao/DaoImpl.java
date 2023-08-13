/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.packet.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * Clase DAO Factory
 * parte del patron abstract factory para generar objetos de tipo DAO
 */
@Component
public class DaoImpl implements Dao{

    //Inicializa un Objeto ProyectDao con Spring
    @Autowired
    ProyectDaoImpl proyectDao;
    
    //Inicializa un Objeto UserDao con Spring
    @Autowired
    UserDaoImpl userDao;
    
    
    @Override
    public ProyectDaoImpl createProyectDao() {
        return proyectDao;
    }

    @Override
    public UserDaoImpl createUserDao() {
        return userDao;

    }
    
}
