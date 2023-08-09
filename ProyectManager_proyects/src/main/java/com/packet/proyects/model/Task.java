/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.proyects.model;

import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author KrauserPC
 */

@Data
public class Task {
    
    @Id 
    private Long _id;
    private String name;
    private String description;
    private String state;
    private Date dateStart;
    private Date dateFinish;
    
}
