FROM amazoncorretto:21.0.5-alpine AS runtime
COPY /build/libs/*.jar /app/server.jar

CMD ["java", "-jar", "/app/server.jar"]