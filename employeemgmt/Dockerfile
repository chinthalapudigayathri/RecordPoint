# Step 1: Base image with Java 21
FROM eclipse-temurin:21-jdk-alpine

# Step 2: Copy the Spring Boot JAR
ARG JAR_FILE=target/employeemgmt-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Step 3: Run the app
ENTRYPOINT ["java","-jar","/app.jar"]
