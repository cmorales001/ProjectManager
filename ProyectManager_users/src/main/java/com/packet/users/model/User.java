/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.users.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

//Modelo User del registro de usuarios en el sistema
// anotacion @Data para generar get set de forma automatica
@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
    private String firstName;
    
  
    private String lastName;
    
 
    private String nickName;
    

    private String email;
    

    private String password;
 
}
