/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.service.web;

import lombok.Data;

/**
 *
 * @author KrauserPC
 */


//clase para recibir las credenciales de usuario desde la web con un metodo post
@Data
public class UserCredentialsDTO {
    private String emailOrNickname;
    private String password;
}
