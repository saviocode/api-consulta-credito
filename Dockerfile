FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY target/api-consulta-credito-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
