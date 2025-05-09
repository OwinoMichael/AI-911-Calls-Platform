# Stage 1: Build Java Spring Boot app
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-jammy

# --- Install system dependencies ---
RUN apt-get update && \
    apt-get install -y python3-venv python3-pip mysql-client && \
    apt-get clean

# Set environment variables
ENV JAVA_HOME=/opt/java/openjdk

# --- Create virtual environment ---
WORKDIR /app
RUN python3 -m venv /app/venv

# Activate venv and install Python packages
COPY requirements.txt .
RUN . /app/venv/bin/activate && pip install --upgrade pip && pip install -r requirements.txt

# Copy startup script
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# --- Copy application files ---
COPY --from=builder /app/target/*.jar app.jar
COPY src/python/ src/python/
COPY src/output/ src/output/

# Set entrypoint and expose app port
EXPOSE 8080
# Use the wait-for-it script to ensure MySQL is ready before starting the app
ENTRYPOINT ["/wait-for-it.sh", "db", "3306", "java", "-jar", "app.jar"]