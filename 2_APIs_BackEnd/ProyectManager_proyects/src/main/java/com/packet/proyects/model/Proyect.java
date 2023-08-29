/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.proyects.model;

import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "proyects")
@Data
public class Proyect {

    @Id
    private Long _id;
    private String name;
    private Long idOwner;
    private String authorization;
    private Date dateStart;
    private Date dateFinish;
    private String codeInvitation;


    private List<User> users;

    private List<Task> tasks;

}
