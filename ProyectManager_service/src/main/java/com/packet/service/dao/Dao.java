/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.packet.service.dao;

/**
 *
 * 
 */
// INTERFACE Dao es la factory(fabrica)de los objetos dao


public interface Dao {   
    ProyectDao createProyectDao();
    UserDao createUserDao();
    
}
