/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.packet.service.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura las reglas de CORS para permitir solicitudes desde un origen específico.
     *
     * @param registry El registro de configuración de CORS.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Configura las reglas de CORS para todas las rutas de la API
            .allowedOrigins("http://localhost:5173") // Define el origen permitido para las solicitudes (URL de su frontend)
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Define los métodos HTTP permitidos para las solicitudes CORS
            .allowCredentials(true); // Habilita el envío de credenciales (por ejemplo, cookies) en las solicitudes CORS
    }
}
