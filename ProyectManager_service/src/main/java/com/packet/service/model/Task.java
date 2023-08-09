/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.service.model;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author KrauserPC
 */
@Data
public class Task {
    
    private Long _id;
    private String name;
    private String description;
    private String state;
    private Date dateStart;
    private Date dateFinish;
    
}
