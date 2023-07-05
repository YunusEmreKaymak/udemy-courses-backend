FROM openjdk:17
EXPOSE 8080
ADD target/course-service.jar course-service.jar
ENTRYPOINT ["java","-jar","course-service.jar"]