FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY . .
RUN ./mvnw -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/target/quarkus-app /app
EXPOSE 8080
ENV PORT=8080
CMD ["java", "-jar", "quarkus-run.jar"]
