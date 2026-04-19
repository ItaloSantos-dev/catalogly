FROM eclipse-temurin:21
LABEL authors="Italo-Santos"
WORKDIR /app
COPY target/catalogy-0.0.1-SNAPSHOT.jar /app/catalogly.jar
ENTRYPOINT ["java", "-jar", "catalogly.jar"]