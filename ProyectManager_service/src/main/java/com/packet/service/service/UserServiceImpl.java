/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.service.service;

import com.packet.service.dao.DaoImpl;
import com.packet.service.dao.UserDao;
import com.packet.service.web.UserCredentialsDTO;
import com.packet.service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(DaoImpl factory) {
        this.userDao = factory.createUserDao();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    // metodo saveUser responsable de verificar si un usuario puede ser registrado en la bdd
    @Override
    public boolean saveUser(User cliente) {
        // bloque try-catch para controlar errores
        try {
            // se verifica si el usuario ya existe por medio del metodo existsUser
            // si ya existe se retorna como falso y no es alamacenado en la bdd
            if (this.existsUser(cliente)) {
                return false;
            }
            // si no existe, se encripta su contraseña con el objeto PasswordEncoder de spring
            cliente.setPassword(this.encryptedPassword(cliente.getPassword()));
            // finalmente de guarda el registro y se retorna true
            userDao.save(cliente);
            return true;
        } catch (Exception e) {
            // en caso de error se devuelve false
            return false;
        }

    }

    // metodo updateUser encargado de realizar validaciones antes de actualizar un registro
    @Override
    public boolean updateUser(User user) {
        // bloque try-catch para controlar errores
        try {
            // se obtiene el registro del usuario a registrar
            User userDb = this.getUserById(user.getId());
            //contraseña obtenida de la sesion(sin encriptar)
            String rawPassword = user.getPassword();

            //contraseña obtenida de la dbb(encriptada)
            String encodedPassword = userDb.getPassword();

            if (!(user.getEmail().equals(userDb.getEmail())) && !user.getEmail().isEmpty()) {
                userDb.setEmail(user.getEmail());
            }
            if (!(user.getNickName().equals(userDb.getNickName())) && !user.getNickName().isEmpty()) {
                userDb.setNickName(user.getNickName());
            }
            if (!(user.getFirstName().equals(userDb.getFirstName())) && !user.getFirstName().isEmpty()) {
                userDb.setFirstName(user.getFirstName());
            }
            if (!(user.getLastName().equals(userDb.getLastName())) && !user.getLastName().isEmpty()) {
                userDb.setLastName(user.getLastName());
            }
            if (!this.authenticate(rawPassword, encodedPassword) && !user.getPassword().isEmpty()) {
                userDb.setPassword(this.encryptedPassword(user.getPassword()));
            }
            userDao.save(user);
            return true;
        } catch (Exception e) {
            // en caso de error se retorna false
            return false;

        }

    }

    @Override
    public User getUserById(Long idUser) {
        try {
            return userDao.findUserById(idUser);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<User> getUserByProyect(List<Long> idUsers) {
        List<User> usersByProyect = new ArrayList<>();
        try {
            for (Long id : idUsers) {
                usersByProyect.add(this.userDao.findUserById(id));
            }
            return usersByProyect;

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public User LoginUser(UserCredentialsDTO user) {

        try {

            User userLogin = userDao.findUserByEmailOrNick(user.getEmailOrNickname(), user.getEmailOrNickname());

            if (userLogin == null) {
                return userLogin;
            }

            //contraseña obtenida del inicio de sesion(sin encriptar)
            String rawPassword = user.getPassword();

            //contraseña obtenida de la dbb(encriptada)
            String encodedPassword = userLogin.getPassword();

            if (this.authenticate(rawPassword, encodedPassword)) {
                return userLogin;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public User getUserByEmailOrNick(String emailOrNick) {
        try {
            return this.userDao.findUserByEmailOrNick(emailOrNick, emailOrNick);
        } catch (Exception e) {
            return null;
        }

    }

    private boolean existsUser(User user) {
        try {
            User userBd = this.userDao.findUserByEmailOrNick(user.getEmail(), user.getNickName());
            return userBd != null;
        } catch (Exception e) {
            return false;
        }

    }

    private String encryptedPassword(String password) {

        String encryptedPassword = passwordEncoder.encode(password);

        return encryptedPassword;
    }

    public boolean authenticate(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
