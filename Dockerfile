FROM openjdk:17
ADD target/flight-search-service-*.jar flight-search-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","flight-search-service.jar"]