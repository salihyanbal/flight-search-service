FROM openjdk:17
COPY --chown=appuser:appuser target/flight-search-service.jar flight-search-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","flight-search-service.jar"]