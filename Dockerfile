# Step 1: Build the project using Maven
FROM maven:3.9.9-sapmachine-17 AS builder

# Set working directory
WORKDIR /app

# Copy the pom.xml and the source code
COPY pom.xml ./
COPY src ./src/

# Run Maven to build the project (this will create the jar file)
RUN mvn clean package -DskipTests

# Step 2: Run the Spring Boot application using OpenJDK
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "demo.jar"]