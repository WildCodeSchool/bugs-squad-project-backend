# YOUR SPRING API ARCHITECHTURE

## Initialization

- Clone this repository
- Create a new Branch
- With MySQL, create the database following your project name (ex: project_name), make sure to have correct access to it.
- Duplicate the content from src/main/resources/application.properties into src/main/resources/application-dev.properties (create file, it will be ignored by Git)
- Add all your sensitive Datastore credentials to the application-dev.properties file
- Add the project informations into the src/main/java/com/templateproject/config/OpenApiConfiguration.java file for Swagger information

## Launching the application

- Run the application with the following command: `mvn spring-boot:run`
- You can now go to http://localhost:8080/swagger-ui.html to see the Swagger UI
- You are now ready to work !

## ADD to application.dev.properties
# Naming strategy
server.servlet.contextPath=/api
spring.mail.default-encoding=UTF-8
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=BugsSquadInd
spring.mail.password=pehfaxknekzmhktb
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
spring.mail.properties.mail.smtp.ssl.trust=smtp.gmail.com
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.writetimeout=5000