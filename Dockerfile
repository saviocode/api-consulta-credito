FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

# cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# build da aplicação
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
