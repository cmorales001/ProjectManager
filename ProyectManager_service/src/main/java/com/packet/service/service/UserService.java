/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.packet.service.service;

import com.packet.service.model.User;
import com.packet.service.web.UserCredentialsDTO;
import java.util.List;

/**
 *
 * @author KrauserPC
 */
public interface UserService {
    
    
    public boolean saveUser(User user);
    
    public User getUserById(Long idUser);
    
    public boolean updateUser(User cliente);
    
    public User LoginUser(UserCredentialsDTO user);
    
    public List<User> getUserByProyect(List<Long> idUsers);
    
    public User getUserByEmailOrNick(String emailOrNick);
}
