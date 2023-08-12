/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.users.service;

import com.packet.users.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.packet.users.dao.UserDao;

/**
 * clase de servicio que se comunica con los métodos DAO
 *  
 */

@Service
public class UserServiceImpl implements UserService {

    // objeto DAO para hacer la comunicación con la capa service
    @Autowired
    private UserDao clienteDao;

    /**
     * guarda un registro de usuario, contactando a la capa DAO
     * @param user registro de usuario a ser guardado
     */
    @Transactional
    @Override
    public void saveUser(User user) {
        clienteDao.save(user);
    }
    
    /**
     * obtiene un registro de usuario por medio de su ID desde la capa DAO
     * @param idCliente ID del cliente 
     * @return objeto DTO User
     */
    @Transactional(readOnly = true)
    @Override
    public User getUserById(Long idCliente) {
        return clienteDao.findById(idCliente).orElse(null);
    }
    
    /**
     * obtiene un registro de usuario por medio de su email o nickname desde la capa DAO
     * @param email email del userio
     * @param nickName nickname del usuario
     * @return objeto DTO User
     */
    @Transactional(readOnly = true)
    @Override
    public User getUserByEmailOrNickName(String email, String nickName) {
        User userBd = this.clienteDao.findUserByEmailOrNickname(email, nickName);
        return userBd;
    }

}
