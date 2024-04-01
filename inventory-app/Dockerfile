#### Stage 1: Build the application

FROM openjdk:17
ADD /build/libs/inventory-app-1.0.0.jar inventory-app.jar
ENTRYPOINT ["java", "-jar", "/inventory-app.jar"]
RUN useradd -m myuser
USER myuser
