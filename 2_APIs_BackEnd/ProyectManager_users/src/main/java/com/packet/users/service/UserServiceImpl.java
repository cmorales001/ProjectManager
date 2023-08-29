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
import java.math.BigInteger;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

/**
 * clase de servicio que se comunica con los métodos DAO
 *
 */
@Service
public class UserServiceImpl implements UserService {

    // objeto DAO para hacer la comunicación con la capa service
    @Autowired
    private UserDao clienteDao;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * guarda un registro de usuario, contactando a la capa DAO
     *
     * @param user registro de usuario a ser guardado
     */
    @Transactional
    @Override
    public void saveUser(User user) {
        clienteDao.save(user);
        
    }

    /**
     * obtiene un registro de usuario por medio de su ID desde la capa DAO
     *
     * @param idCliente ID del cliente
     * @return objeto DTO User
     */
    @Transactional(readOnly = true)
    @Override
    public User getUserById(Long idCliente) {
        return clienteDao.findById(idCliente).orElse(null);
    }

    /**
     * obtiene un registro de usuario por medio de su email o nickname desde
     * un procedimiento almacenado
     *
     * @param email email del usuario
     * @param nickName nickname del usuario
     * @return objeto DTO User
     */
    @Override
    public User getUserByEmailOrNickName(String email, String nickName) {
        // Configuramos para usar el Store procedure
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("getUserByEmailOrNickname");
        storedProcedure.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_nickname", String.class, ParameterMode.IN);
        // enlazamos los parametros
        storedProcedure.setParameter("p_email", email);
        storedProcedure.setParameter("p_nickname", nickName);
        // ejecutamos el Store Procedure
        storedProcedure.execute();
        try {
            // extraemos y devolvemos el resultado
            Object[] result = (Object[]) storedProcedure.getSingleResult();

            User user = new User();
            user.setId(((BigInteger) result[0]).longValue());
            user.setFirstName((String) result[1]);
            user.setLastName((String) result[2]);
            user.setNickName((String) result[3]);
            user.setEmail((String) result[4]);
            user.setPassword((String) result[5]);
            return user;
        } catch (Exception e) {
            // en caso de problemas retornamos null
            return null;
        }

    }

}
