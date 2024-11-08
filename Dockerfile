# Utilise une image de base JDK 17 (ou une autre version selon votre projet)
FROM openjdk:17-jdk-slim



# Copier le jar de l'application (remplacez 'app.jar' par le nom correct)
ADD target/devops-integration.jar devops-integration.jar

# Expose le port 8080 (ou celui que vous utilisez pour Spring Boot)
EXPOSE 8082

# Commande de démarrage
ENTRYPOINT ["java", "-jar", "/devops-integration.jar"]
