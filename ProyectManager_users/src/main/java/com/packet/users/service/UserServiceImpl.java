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

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao clienteDao;

    @Transactional
    @Override
    public void saveUser(User cliente) {
        clienteDao.save(cliente);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(Long idCliente) {
        return clienteDao.findById(idCliente).orElse(null);
    }

    @Override
    public User getUserByEmailOrNickName(String email, String nickName) {
        User userBd = this.clienteDao.findUserByEmailOrNickname(email, nickName);
        return userBd;
    }

}
