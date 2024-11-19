# Stage 1: build
# Start with a Maven image that includes JDK 21
FROM maven:3.9.8-amazoncorretto-21 AS buildApp

# Copy source code and pom.xml file to /app folder
WORKDIR /app-multiservice
COPY pom.xml .
COPY src ./src

# Build source code with maven
RUN mvn package -DskipTests

#Stage 2: create image
# Start with Amazon Correto JDK 21
FROM amazoncorretto:21.0.4

# Set working folder to App and copy complied file from above step
WORKDIR /app
COPY --from=buildApp /app-multiservice/target/*.jar multiservice.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "multiservice.jar"]
