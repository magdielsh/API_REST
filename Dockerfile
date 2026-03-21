FROM eclipse-temurin:21-jdk
ARG JAR_FILE=target/control.visitas-0.0.1.jar
COPY ${JAR_FILE} app_visitas.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_visitas.jar"]


#FROM eclipse-temurin:21-jdk
#WORKDIR /app
#COPY . .
#RUN ./mvnw clean package -DskipTests
#FROM eclipse-temurin:21-jre
#WORKDIR /app
#COPY --from=builder /app/target/*.jar app_visitas.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app_visitas.jar"]