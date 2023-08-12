/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.packet.users.dao;

import com.packet.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 *  Capa Dao que contiene todos los metodos para realizar operacion Crud con Spring
 */

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    

    /**
     * consulta personalizada para obtener un usario por su email o nickname desde la BDD
     * @param email email del usuario
     * @param nickname nickname del usuario
     * @return ObjetoDTO de usuario a la capa service
     */
    @Query("SELECT user FROM User user WHERE user.email = :email OR user.nickName = :nickname")
    public User findUserByEmailOrNickname(@Param("email") String email, @Param("nickname") String nickname);

}
