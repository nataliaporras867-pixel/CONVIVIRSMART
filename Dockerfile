FROM openjdk:21
COPY "./target/CONVIVIRSMART-0.0.1-SNAPSHOT.jar" app.jar
EXPOSE 8215
ENTRYPOINT ["java","-jar","/app.jar"]