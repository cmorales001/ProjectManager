/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.service.model;

import java.util.Date;
import java.util.List;
import lombok.Data;



@Data
public class Proyect {

    private Long _id;
    private String name;
    private Long idOwner;
    private String authorization;
    private Date dateStart;
    private Date dateFinish;
    private String codeInvitation;
    private List<ProyectUser> users;
    private List<Task> tasks;
       
}
