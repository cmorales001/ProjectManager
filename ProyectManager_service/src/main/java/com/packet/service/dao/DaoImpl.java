/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.packet.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 * @author KrauserPC
 */
@Component
public class DaoImpl implements Dao{

    @Autowired
    ProyectDaoImpl proyectDao;
    
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
