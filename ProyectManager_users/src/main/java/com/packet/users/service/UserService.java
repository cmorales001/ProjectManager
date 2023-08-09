/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.packet.users.service;

import com.packet.users.model.User;

/**
 *
 * @author KrauserPC
 */
public interface UserService {
    
    
    public void saveUser(User user);
    
    public User getUserById(Long idUser);
    
    public User getUserByEmailOrNickName(String email, String nickName);
    
}
