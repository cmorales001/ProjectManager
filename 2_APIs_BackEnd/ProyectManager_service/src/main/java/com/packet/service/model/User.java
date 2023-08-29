/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.service.model;


import lombok.Data;

//Modelo User del registro de usuarios en el sistema
// anotacion @Data para generar get set de forma automatica
@Data
public class User  {

    private Long id;
    
    
    private String firstName;
    
  
    private String lastName;
    
 
    private String nickName;
    

    private String email;
    

    private String password;
 
}
