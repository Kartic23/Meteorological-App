FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar meteoapp.jar
ENTRYPOINT ["java","-jar","/meteoapp.jar"]
EXPOSE 8080