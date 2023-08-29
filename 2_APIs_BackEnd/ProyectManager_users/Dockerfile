
# Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
# Click nbfs://nbhost/SystemFileSystem/Templates/Other/Dockerfile to edit this template

# Utiliza una imagen base de OpenJDK 11
FROM adoptopenjdk/openjdk11

# Copia el archivo JAR de tu API al contenedor
COPY target/*.jar app.jar

# Define el puerto en el que tu aplicación API va a escuchar
EXPOSE 8081

# Define el comando de inicio para ejecutar tu aplicaciÃ³n
CMD ["java","-jar","/app.jar"]

