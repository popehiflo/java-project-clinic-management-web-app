# Spring Boot 3 - Vanilla JS: Clínica Odontológica, gestión básica 🏥
**Proyecto Web Fullstack**  
*Gestionar la información básica de Pacientes, Odontólogos, Turnos o Citas.*  
`H2`, `MySQL`, `JPA`, `Hibernate`, `Spring Boot`, `Spring Data JPA`, `Spring MVC`, `Spring Security`, 
`JWT`, `JUnit`,`HTML`, `CSS`, `Bootstrap`, `JS`.

## Backend Java 🛠️
*API Rest usando Java con Spring Boot 3.3.1 y base de datos H2/MySQL. Uso de JPA con Hibernate para la persistencia de datos. También tiene validación de datos, manejo de excepciones, uso adecuado del protocolo HTTP en estándar REST y mucho más*
* [H2](https://www.h2database.com/html/main.html) - Base de Datos relacionales hecho con Java SQL
* [MySQL 8](https://dev.mysql.com/downloads/mysql/) - RDBMS de código abierto
* [Java 17](https://www.oracle.com/java/technologies/downloads/#java17) - Java SE Development Kit 17
* [Spring](https://spring.io/) - El framework web mas usado
    * [Spring Boot]()
    * [Spring Data JPA]()
    * [Spring MVC]()
    * [Spring Security]()
* [Maven](https://maven.apache.org/) - Herramienta de gestión de dependencias

## Ejecutando localmente 🚀
Esta es una aplicación [Spring Boot](https://spring.io/guides/gs/spring-boot/) construida usando [Maven](https://maven.apache.org/). Se puede compilar y ejecutar desde la línea de comandos:
```
... despues de clonar el repositorio
cd spring-boot-3-vanillajs-clinica-web-app
mvn spring-boot:run
``` 
Luego navegar hacia `http://localhost:8080/`  
Recuerda revisar el archivo [application.properties](src/main/resources/application.properties) para activar el perfil y el gestor de base de datos usara.
## Postman 📎
Colección de requests [Collection SB3-JS-Clínica](https://www.getpostman.com/collections/1abd828e2e340b18f803) que puedes descargar e importar en tu cliente Postman.
Se lista los distintos endpoints de la API. Tiene variables de entorno (URL_BASE, TOKEN).
## Descripción 💬
API REST que permite el CRUD o ABM de los diferentes Enums, Entidades aquí listadas:
- [Paciente](src/main/java/io/github/popehiflo/clinica/entity/Paciente.java): Paciente.
- [Odontólogo](src/main/java/io/github/popehiflo\clinica/entity/Odontologo.java): Odontólogo o Doctor.
- [Turno](src/main/java/io/github/popehiflo/clinica/entity/Turno.java): Turno o Cita del Paciente con un Odontólogo.

## Si encuentra un error o quiere sugerir una mejora 📧
Siéntase libre de informar problemas/errores aquí:
[https://github.com/popehiflo/spring-boot-3-vanillajs-clinica-web-app/issues](https://github.com/popehiflo/spring-boot-3-pizzeria-backend/issues)
## Licencia 📄
[MIT License](LICENSE)

