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

/**
 *
 * Capa Service la cual contiene la lógica de negocio del sistema Se comunica
 * con la capa DAO
 */
@Service
public class UserServiceImpl implements UserService {

    // conexion a la CapaDao
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(DaoImpl factory) {
        //metodo contructor que inicializa un objeto UserDao con su factory
        this.userDao = factory.createUserDao();
    }

    // objeto de seguridad de Spring
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * guarda un registro de usuario conectandose a la capa DAO
     *
     * @param user
     * @return
     */
    private boolean save(User user) {
        try {
            this.userDao.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * actualiza un registro de usuario conectandose a la capa DAO
     *
     * @param user
     * @return
     */
    private boolean update(User user) {
        try {
            this.userDao.update(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * método get para obtener un usario por su ID
     *
     * @param idUser ID del usuario
     * @return Objeto DTO User o Null en caso de error
     */
    @Override
    public User getUserById(Long idUser) {
        try {
            return userDao.findUserById(idUser);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Recuperar un usuario por su Email o Nickname desde la BDD
     *
     * @param emailOrNick Nick o Email del usuario a buscar
     * @return Objeto DTO User en caso de existir, null en caso de error o no
     * existir
     */
    private User getUserByEmailOrNick(String email, String nickName) {
        try {
            return this.userDao.findUserByEmailOrNick(email, nickName);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Recuperar un usuario por su Email o Nickname desde la BDD
     *
     * @param emailOrNick Nick o Email del usuario a buscar
     * @return Objeto DTO User en caso de existir, null en caso de error o no
     * existir
     */
    @Override
    public User getUserByEmailOrNick(String emailOrNick) {
        return this.getUserByEmailOrNick(emailOrNick, emailOrNick);
    }

    /**
     * responsable de registrar un usuario en la bdd despues de validar
     * restricciones
     *
     * @param user Objeto DTO User
     * @return boolean para confirmar si fue almacenado
     */
    @Override
    public boolean saveUser(User user) {

        // se verifica si el usuario ya existe por medio del metodo existsUser
        // si ya existe se retorna como falso y no es almacenado en la bdd
        if (this.existsUser(user)) {
            return false;
        }

        // si no existe, se encripta su contraseña con el objeto PasswordEncoder de spring
        user.setPassword(this.encryptedPassword(user.getPassword()));

        // finalmente se envía a la capa DAO y se retorna true
        return this.save(user);

    }

    /**
     * metodo updateUser encargado de realizar validaciones antes de actualizar
     * un registro
     *
     * @param user Objeto DTO user a ser actualizado
     * @return boolean para confirmar si fue registrado
     */
    @Override
    public boolean updateUser(User user) {

        // se obtiene el registro del usuario a registrar
        User userDb = this.getUserById(user.getId());
        //contraseña obtenida de la sesion(sin encriptar)
        String rawPassword = user.getPassword();

        //contraseña obtenida de la dbb(encriptada)
        String encodedPassword = userDb.getPassword();

        // se actualiza los cambios
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

        return this.update(user);

    }

    /**
     * metodo para obtener los usuarios pertenecientes a un proyecto buscados
     * por ID
     *
     * @param idUsers Lista de Longs con los usuario pertenecientes a un
     * proyecto
     * @return Lista de usuario
     */
    @Override
    public List<User> getUserByProyect(List<Long> idUsers) {
        List<User> usersByProyect = new ArrayList<>();
        try {
            for (Long id : idUsers) {
                usersByProyect.add(this.getUserById(id));
            }
            return usersByProyect;

        } catch (Exception e) {
            return null;
        }

    }

    /**
     * metodo responsable de comprobar las credenciales de un usuario para
     * inicio de sesion
     *
     * @param user Objeto DTO UserCredentialsDTO
     * @return Objeto DTO User en caso de ser correctas las credenciales, y en
     * caso de ser incorrectas NULL
     */
    @Override
    public User LoginUser(UserCredentialsDTO user) {

        // bsuscamos el usuario en la capa Dao por el parametro Email e  Nick para evitar repeticion
        User userLogin = this.getUserByEmailOrNick(user.getEmailOrNickname());

        // si no existen registros retorna null 
        if (userLogin == null) {
            return userLogin;
        }

        // si encuentra algún registro procede a la verificación de la contraseña
        //contraseña obtenida del inicio de sesion(sin encriptar)
        String rawPassword = user.getPassword();

        //contraseña obtenida de la dbb(encriptada)
        String encodedPassword = userLogin.getPassword();

        // usa el método local authenticate para para verificar con ayuda de los objetos de Spring
        if (this.authenticate(rawPassword, encodedPassword)) {
            return userLogin;
        } else {
            return null;
        }

    }

    /**
     * Comprueba la existencia de un usuario
     *
     * @param user Objeto DTO User
     * @return boolean, (True) si existe, (False si no existe)
     */
    private boolean existsUser(User user) {

        User userBd = this.getUserByEmailOrNick(user.getEmail(), user.getNickName());
        return userBd != null;

    }

    /**
     * Encripta la contraseña con ayuda del objeto passwordEncoder de Spring
     *
     * @param password contreña a encriptar
     * @return contraseña encriptada
     */
    private String encryptedPassword(String password) {

        String encryptedPassword = passwordEncoder.encode(password);

        return encryptedPassword;
    }

    /**
     * Comprueba que la contraseña enviada desde el front sea igual a la
     * registrada en la bdd con ayuda del método matches del Objeto
     * passwordEncoder de Spring (solo es posible verificar de esta manera)
     *
     * @param rawPassword contraseña sin encriptar
     * @param encodedPassword contraseña encriptada recuperada de la BDD
     * @return boolean, (True) si son iguales, (False) si son diferentes
     */
    public boolean authenticate(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
