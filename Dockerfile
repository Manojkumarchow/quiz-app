# Use the official OpenJDK 17 image as a base image
FROM openjdk:17-oracle

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot executable JAR file into the container
COPY JARs/session-management.jar app.jar

# Expose the port that your Spring Boot application listens on (change 8080 to the port your app uses)
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
