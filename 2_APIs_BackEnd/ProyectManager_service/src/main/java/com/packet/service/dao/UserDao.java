/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.packet.service.dao;

import com.packet.service.model.User;

/**
 *
 * @author KrauserPC
 */
public interface UserDao {

    //metodo DAO para obtener un usuario por su id desde la bdd
    public User findUserById(Long id);

    //metodo DAO para guardar un usuario a la bdd
    public boolean save(User user);

    //metodo DAO para actualizar un registro de usuario en la bdd
    public boolean update(User user);

    //metodo DAO para obtener un usuario por su email o nickname desde la bdd
    public User findUserByEmailOrNick(String email, String nick);

}
