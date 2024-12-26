FROM amazoncorretto:21.0.5
ADD target/swagger-api-docx-1.0.0.jar swagger-api-docx-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "swagger-api-docx-1.0.0.jar"]
